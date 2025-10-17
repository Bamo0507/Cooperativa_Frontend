package app.cooperativa.presentation.mainflow.directiva.pagos.pendingPaymentDetail

import androidx.lifecycle.SavedStateHandle
import app.cooperativa.domain.directiva.DPendingPayRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DPendingPayViewModel(
    private val repository: DPendingPayRepository,
    private val paymentId: String
): ViewModel() {
    private val _uiState = MutableStateFlow(DPendingPayState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        loadPayment()
    }

    fun loadPayment() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val p = repository.getPaymentById(paymentId)
                _uiState.update { it.copy(payment = p, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    // actualizar comentario
    fun onCommentChange(comment: String) {
        _uiState.update { it.copy(commentInput = comment) }
    }

    fun openRejectDialog() {
        _uiState.update { it.copy(showRejectDialog = true) }
    }

    fun closeRejectDialog() {
        _uiState.update { it.copy(showRejectDialog = false) }
    }

    // aprobar pago
    fun onApprove() {
        // TODO: lógica de aprobación
    }

    // negar pago
    fun onReject() {
        // TODO: lógica de rechazo (usar uiState.value.commentInput)
        // Al terminar, cierra el diálogo:
        _uiState.update { it.copy(showRejectDialog = false) }
    }

}
