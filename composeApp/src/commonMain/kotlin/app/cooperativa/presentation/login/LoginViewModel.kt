package app.cooperativa.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.domain.localstorage.PreferencesLocalStorage
import app.cooperativa.domain.login.LoginRepository
import app.cooperativa.domain.login.LoginResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val prefs: PreferencesLocalStorage
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onUsernameChange(new: String) {
        _state.update { it.copy(username = new, error = null) }
    }

    fun onPasswordChange(new: String) {
        _state.update { it.copy(password = new, error = null) }
    }

    // pública: valida y lanza login
    fun submitLoginIfValid(onSuccess: (userType: String) -> Unit) {
        val username = _state.value.username
        val password = _state.value.password
        if (username.isBlank() || password.isBlank()) {
            _state.update {
                it.copy(error = "Usuario y contraseña son requeridos")
            }
            return
        }
        submitLogin(onSuccess)
    }

    // interna: asume que ya fue validado
    private fun submitLogin(onSuccess: (userType: String) -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val username = _state.value.username
            val password = _state.value.password

            when (val result = loginRepository.login(username, password)) {
                is LoginResult.Success -> {
                    prefs.setAccessToken(result.accessToken)
                    prefs.setUser_type(result.userType)
                    prefs.setHasLoggedIn(true)

                    _state.update {
                        it.copy(
                            isLoading = false,
                            isLoggedIn = true,
                            userType = result.userType
                        )
                    }

                    onSuccess(result.userType)
                }

                is LoginResult.Failure -> {
                    _state.update {
                        it.copy(isLoading = false, error = "Usuario o contraseña incorrectos")
                    }
                }
            }
        }
    }
}