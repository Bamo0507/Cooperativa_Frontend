package app.cooperativa.domain.login

import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class CoopLoginRepository(
    private val httpClient: HttpClient,
    private val baseUrl: String = "https://dev.cooperativa-isp.cc"
) : LoginRepository {

    private val json = Json { ignoreUnknownKeys = true }

    @Serializable
    private data class LoginRequest(val user_name: String, val pass_code: String)

    @Serializable
    private data class OkPayload(val access_token: String)

    @Serializable
    private data class ErrPayload(val message: String)

    @Serializable
    private data class LoginResponseRoot(
        val Ok: OkPayload? = null,
        val Err: ErrPayload? = null
    )

    override suspend fun login(userName: String, passCode: String): LoginResult {
        return try {
            val response: HttpResponse = httpClient.request("$baseUrl/general/login") {
                method = HttpMethod.Get
                contentType(ContentType.Application.Json)       // <- igual que Insomnia
                // Si instalaste ContentNegotiation { json(...) }, puedes pasar directamente el objeto:
                setBody(LoginRequest(user_name = userName, pass_code = passCode))
                // No agregues Accept ni otros headers raros.
            }

            val bodyText = response.bodyAsText()

            if (response.status.value !in 200..299) {
                return LoginResult.Failure("HTTP ${response.status.value}: ${bodyText.ifBlank { "sin cuerpo" }}")
            }
            if (bodyText.isBlank()) {
                return LoginResult.Failure("Respuesta vacía del servidor")
            }

            val parsed = json.decodeFromString(LoginResponseRoot.serializer(), bodyText)

            parsed.Ok?.let {
                val token = it.access_token
                if (token.isBlank()) return LoginResult.Failure("Token inválido recibido")
//                TODO: CAMBIAR PARA TENER EL TYPE QUE MANDE EL BACKEND
                return LoginResult.Success(token, "directive") // default mientras backend envía user_type
            }
            parsed.Err?.let { return LoginResult.Failure(it.message) }

            LoginResult.Failure("Respuesta inesperada del login")
        } catch (e: Exception) {
            LoginResult.Failure("Error de login: ${e.message ?: "desconocido"}")
        }
    }
}