package app.cooperativa.domain.login

import app.cooperativa.graphql.GraphQLClientProvider

interface LoginRepository {
    suspend fun login(userName: String, passCode: String): LoginResult
}

class CoopLoginRepository(
    private val clientProvider: GraphQLClientProvider
) : LoginRepository {

    override suspend fun login(userName: String, passCode: String): LoginResult {
        val response = clientProvider.login(userName = userName, passCode = passCode)

        if (response.hasErrors()) {
            val msgs = response.errors?.joinToString { it.message } ?: "Error desconocido del servidor"
            return LoginResult.Failure(msgs)
        }

        val payload = response.data?.login
            ?: return LoginResult.Failure("Respuesta vacía del servidor")

        payload.Ok?.let { okBlock ->
            val token = okBlock.access_token
            val userType = okBlock.user_type.ifBlank { "affiliate" } // fallback si es vacío
            if (token.isBlank()) {
                return LoginResult.Failure("Token inválido recibido")
            }
            return LoginResult.Success(token, userType)
        }

        payload.Err?.let { errBlock ->
            return LoginResult.Failure(errBlock.message)
        }

        return LoginResult.Failure("Respuesta no esperada del login")
    }
}