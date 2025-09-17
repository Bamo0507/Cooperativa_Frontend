package app.cooperativa.domain.login

interface LoginRepository {
    suspend fun login(userName: String, passCode: String): LoginResult
}