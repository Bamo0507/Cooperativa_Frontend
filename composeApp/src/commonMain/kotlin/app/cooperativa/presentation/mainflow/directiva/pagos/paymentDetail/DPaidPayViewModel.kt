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
    private val ticketRepo: app.cooperativa.domain.general.TicketViewerRepository,
    private val prefs: app.cooperativa.domain.localstorage.PreferencesLocalStorage,
    private val paymentId: String
): ViewModel() {

    private val _uiState: MutableStateFlow<DPaidPayState> = MutableStateFlow(DPaidPayState())
    val uiState get() = _uiState.asStateFlow()

    init { loadPayment() }

    fun loadPayment() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val p = repository.getPaymentById(paymentId)
                _uiState.update { it.copy(isLoading = false, payment = p) }

                val ticketId = p.photoPath
                if (ticketId.isNotBlank()) {
                    fetchTicket(ticketId)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    private suspend fun fetchTicket(ticketId: String) {
        val token = prefs.getAccessToken().orEmpty()
        if (token.isBlank()) return

        runCatching { ticketRepo.fetchTicket(token, ticketId) }
            .onSuccess { res ->
                _uiState.update {
                    it.copy(
                        ticketUrl = res.url,
                        ticketBytes = res.bytes
                    )
                }
            }
    }

    fun openTicketViewer() { _uiState.update { it.copy(showTicketViewer = true) } }
    fun closeTicketViewer() { _uiState.update { it.copy(showTicketViewer = false) } }
}