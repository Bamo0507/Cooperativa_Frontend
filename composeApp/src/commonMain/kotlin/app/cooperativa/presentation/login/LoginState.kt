package app.cooperativa.presentation.login

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val userType: String? = null,
    val isLoggedIn: Boolean = false
)
