package app.cooperativa.presentation.mainflow.directiva.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<SplashState> = MutableStateFlow(SplashState())
    val uiState = _uiState.asStateFlow()

    init {
        //De momento dejar a lo bruto el tiempo de espera solo para que se vea
        // todo: persistir sesion con data store y en base a eso hacer el cambio
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(3000)
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}