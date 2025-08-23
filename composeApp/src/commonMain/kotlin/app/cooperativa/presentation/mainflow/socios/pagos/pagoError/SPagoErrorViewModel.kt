package app.cooperativa.presentation.mainflow.socios.pagos.pagoError

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.domain.socios.SPagoErrorRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SPagoErrorViewModel(
    private val repository: SPagoErrorRepository,
    private val paymentId: String
): ViewModel() {
    private val _uiState: MutableStateFlow<SPagoErrorState> = MutableStateFlow(SPagoErrorState())
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData(){
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isLoading = true,
                )
            }

            delay(1500)

            try {
                val message = repository.getDirectiveErrorMessage(paymentId)

                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        directiveMessage = message
                    )
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = "Error obteniendo el mensaje"
                    )
                }
            }
        }
    }
}