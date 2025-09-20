package app.cooperativa.presentation.mainflow.socios.pagos.pagoStatus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.domain.localstorage.PreferencesLocalStorage
import app.cooperativa.domain.socios.SPagosStatusRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SPagosStatusViewModel(
    private val repository: SPagosStatusRepository,
    private val prefs: PreferencesLocalStorage
) : ViewModel() {

    private val _uiState = MutableStateFlow(SPagosStatusState())
    val uiState = _uiState.asStateFlow()

    init { loadData() }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val accessToken = prefs.getAccessToken()
                val pagosUser = repository.getPagoStatusByUser(accessToken)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        pagosStatus = pagosUser
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error desconocido"
                    )
                }
            }
        }
    }
}
