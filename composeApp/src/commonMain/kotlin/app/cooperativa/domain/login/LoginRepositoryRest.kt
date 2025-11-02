package app.cooperativa.domain.login

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class CoopLoginRepository(
    private val httpClient: HttpClient,
    private val baseUrl: String = "https://dev.cooperativa-isp.cc"
) : LoginRepository {

    private val json = Json { ignoreUnknownKeys = true; explicitNulls = false }

    @Serializable
    private data class OkPayload(val access_token: String? = null, val user_type: String? = null)
    @Serializable
    private data class ErrPayload(val message: String)
    @Serializable
    private data class LoginResponseRoot(val Ok: OkPayload? = null, val Err: ErrPayload? = null)

    override suspend fun login(userName: String, passCode: String): LoginResult {
        return try {
            val response: HttpResponse = httpClient.get("$baseUrl/general/login") {
                url {
                    parameters.append("user_name", userName)
                    parameters.append("pass_code", passCode)
                }
                // No body, no contentType en GET
            }

            val code = response.status.value
            val bodyText = response.bodyAsText()

            if (code !in 200..299) {
                return LoginResult.Failure("HTTP $code: ${bodyText.ifBlank { "sin cuerpo" }}")
            }
            if (bodyText.isBlank()) {
                val fromCookie = tokenFromCookies(response)
                return if (fromCookie != null) {
                    LoginResult.Success(fromCookie, userType = "desconocido")
                } else {
                    LoginResult.Failure("Respuesta vacía del servidor")
                }
            }

            val parsedRoot = runCatching { json.decodeFromString(LoginResponseRoot.serializer(), bodyText) }.getOrNull()
            if (parsedRoot != null) {
                parsedRoot.Ok?.let { ok ->
                    val token = ok.access_token?.takeIf { it.isNotBlank() }
                        ?: tokenFromCookies(response)
                    val type  = ok.user_type ?: "desconocido"

                    return if (token != null) LoginResult.Success(token, type)
                    else LoginResult.Failure("Token inválido o ausente en respuesta")
                }
                parsedRoot.Err?.let { return LoginResult.Failure(it.message) }
            }

            val asJson = runCatching { Json.parseToJsonElement(bodyText).jsonObject }.getOrNull()
            if (asJson != null) {
                val token = asJson["token"]?.jsonPrimitive?.contentOrNull
                    ?: asJson["access_token"]?.jsonPrimitive?.contentOrNull
                    ?: tokenFromCookies(response)

                val type = asJson["user_type"]?.jsonPrimitive?.contentOrNull ?: "desconocido"

                if (token != null) return LoginResult.Success(token, type)
            }

            val cookieToken = tokenFromCookies(response)
            if (cookieToken != null) return LoginResult.Success(cookieToken, "desconocido")

            LoginResult.Failure("Respuesta inesperada del login")
        } catch (e: Exception) {
            LoginResult.Failure("Error de login: ${e.message ?: "desconocido"}")
        }
    }

    private fun tokenFromCookies(response: HttpResponse): String? {
        val cookies = response.headers.getAll(HttpHeaders.SetCookie).orEmpty().joinToString(";")
        val candidates = listOf("session", "jwt", "token")
        for (name in candidates) {
            val value = Regex("""\b${name}=([^;]+)""").find(cookies)?.groupValues?.getOrNull(1)
            if (!value.isNullOrBlank()) return value
        }
        return null
    }
}
