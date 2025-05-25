package app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.domain.directiva.DPaymentsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DPaymentsViewModel(
    private val repository: DPaymentsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DPaymentsState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    /**
     * Carga las listas de pagos y moras desde el repositorio.
     */
    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // delay time para mostrar loading y por buena practica
            delay(1500)

            try {
                val allPayments = repository.getAllPaymentsBasicInfo()
                val allFines = repository.getAllFines()

                val pending = allPayments.filter { it.isPaymentPending }
                val paid = allPayments.filter { !it.isPaymentPending }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        pendingPayments = pending,
                        paidPayments = paid,
                        fines = allFines,
                        allPaidPayments = paid,
                        allFinesList = allFines
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    /**
     * Cambia la pestaña activa y resetea búsqueda y contenidos filtrados.
     */
    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index, searchQuery = "") }
        when (index) {
            1 -> _uiState.update { it.copy(paidPayments = it.allPaidPayments) }
            2 -> _uiState.update { it.copy(fines = it.allFinesList) }
        }
    }

    /**
     * Actualiza el texto de búsqueda y filtra las listas correspondientes.
     */
    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        val q = query.trim().lowercase()
        val state = _uiState.value
        when (state.selectedTabIndex) {
            1 -> {
                val filtered = if (q.isEmpty()) state.allPaidPayments
                else state.allPaidPayments.filter {
                    it.paymentName.lowercase().contains(q)
                            || it.username.lowercase().contains(q)
                }
                _uiState.update { it.copy(paidPayments = filtered) }
            }
            2 -> {
                val filtered = if (q.isEmpty()) state.allFinesList
                else state.allFinesList.filter {
                    it.userName.lowercase().contains(q)
                }
                _uiState.update { it.copy(fines = filtered) }
            }
        }
    }
}