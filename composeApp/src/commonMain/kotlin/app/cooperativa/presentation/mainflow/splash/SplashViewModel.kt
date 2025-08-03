package app.cooperativa.presentation.mainflow.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.data.preferences.PreferencesDataStore
import app.cooperativa.domain.localstorage.PreferencesLocalStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel(
    private val dataStore: PreferencesLocalStorage
) : ViewModel() {
    private val _uiState: MutableStateFlow<SplashState> = MutableStateFlow(SplashState())
    val uiState = _uiState.asStateFlow()

    init {
        //De momento dejar a lo bruto el tiempo de espera solo para que se vea
        // todo: persistir sesion con data store y en base a eso hacer el cambio
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(5000)

            _uiState.update {
                it.copy(
                    userType = dataStore.getUser_type(),
                    hasLoggedIn = dataStore.getHasLoggedIn()
                )
            }

            _uiState.update { it.copy(isLoading = false) }
        }
    }
}