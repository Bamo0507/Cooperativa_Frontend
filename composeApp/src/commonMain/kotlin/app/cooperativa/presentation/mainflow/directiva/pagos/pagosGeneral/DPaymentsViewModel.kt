package app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.domain.directiva.DPaymentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DPaymentsViewModel(
    private val repository: DPaymentsRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<DPaymentsScreen> = MutableStateFlow(DPaymentsScreen(
        isLoading = true
    ))
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    /**
     * Carga todos los datos iniciales desde el repositorio.
     */
    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
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
                        fines = allFines
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message)
                }
            }
        }
    }

    /**
     * Cambia la pestaña activa (0,1 o 2) y limpia la búsqueda.
     */
    fun onTabSelected(index: Int) {
        _uiState.update {
            it.copy(
                selectedTabIndex = index,
                searchQuery = ""
            )
        }
    }

    /**
     * Actualiza el texto de búsqueda y filtra las listas de Pagados o Moras.
     */
    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        _uiState.value.let { state ->
            val search = query.trim().lowercase()
            if (state.selectedTabIndex == 1) {
                val filteredPaid = state.paidPayments
                    .filter {
                        it.paymentName.lowercase().contains(search)
                                || it.username.lowercase().contains(search)
                    }
                _uiState.update { it.copy(paidPayments = filteredPaid) }
            } else if (state.selectedTabIndex == 2) {
                val filteredFines = state.fines
                    .filter { fine ->
                        fine.userName.lowercase().contains(search)
                    }
                _uiState.update { it.copy(fines = filteredFines) }
            }
        }
    }
}
