package app.cooperativa.domain.login

import app.cooperativa.graphql.GraphQLClientProvider

interface LoginRepository {
    suspend fun login(userName: String, passCode: String): LoginResult
}

class CoopLoginRepository(
    private val clientProvider: GraphQLClientProvider
) : LoginRepository {

    override suspend fun login(userName: String, passCode: String): LoginResult {
        val response = clientProvider.client.mutation(
            LoginMutation(user_name = userName, pass_code = passCode)
        ).execute()

        // Si hay errores de transporte o GraphQL-level
        if (response.hasErrors()) {
            val msgs = response.errors?.joinToString { it.message } ?: "Error desconocido"
            return LoginResult.Failure(msgs)
        }

        val payload = response.data?.login
            ?: return LoginResult.Failure("Respuesta vacía del servidor")

        // Según tu schema, puede venir Ok o Err
        payload.Ok?.let { okBlock ->
            val token = okBlock.access_token
            val userType = okBlock.user_type ?: "affiliate" // default si no viene
            if (token.isNullOrBlank()) {
                return LoginResult.Failure("Token inválido recibido")
            }
            return LoginResult.Success(token, userType)
        }

        payload.Err?.let { errBlock ->
            return LoginResult.Failure(errBlock.message ?: "Error sin mensaje")
        }

        return LoginResult.Failure("Respuesta no esperada del login")
    }
}