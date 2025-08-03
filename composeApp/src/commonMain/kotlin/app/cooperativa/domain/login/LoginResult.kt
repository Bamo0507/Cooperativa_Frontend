package app.cooperativa.domain.login

sealed class LoginResult {
    data class Success(val accessToken: String, val userType: String = "affiliate") : LoginResult()
    data class Failure(val message: String) : LoginResult()
}