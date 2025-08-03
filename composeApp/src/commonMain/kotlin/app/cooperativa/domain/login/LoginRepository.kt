package app.cooperativa.domain.login

import app.cooperativa.graphql.GraphQLClientProvider

interface LoginRepository {
    suspend fun login(userName: String, passCode: String): LoginResult
}