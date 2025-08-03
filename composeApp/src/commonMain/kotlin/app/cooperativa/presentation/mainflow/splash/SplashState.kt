package app.cooperativa.presentation.mainflow.splash

data class SplashState(
    var hasLoggedIn: Boolean = false,
    var userType: String = "affiliate",
    var isLoading: Boolean = false
)

