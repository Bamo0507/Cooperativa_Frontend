package app.cooperativa.presentation.mainflow.directiva.pagos.paymentDetail

import androidx.compose.runtime.isTraceInProgress
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.domain.directiva.DPaymentsDetailRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DPaidPayViewModel(
    private val repository: DPaymentsDetailRepository,
    private val paymentId: Int
): ViewModel() {
    private val _uiState: MutableStateFlow<DPaidPayState> = MutableStateFlow(DPaidPayState())
    val uiState get() = _uiState.asStateFlow()

    init {
        loadPayment()
    }

    fun loadPayment() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isLoading = true
                )
            }

            delay(1500)

            try {
                val p = repository.getPaymentById(paymentId)
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        payment = p
                    )
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }
}