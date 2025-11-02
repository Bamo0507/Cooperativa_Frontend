package app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.domain.directiva.DPaymentsRepository
import app.cooperativa.graphql.type.FineStatus
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
                        allPendingPayments = pending,
                        allPaidPayments = paid,
                        allFinesList = allFines
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index, searchQuery = "") }
        when (index) {
            0 -> _uiState.update { it.copy(pendingPayments = it.allPendingPayments) }
            1 -> _uiState.update { it.copy(paidPayments = it.allPaidPayments) }
            2 -> _uiState.update { it.copy(fines = it.allFinesList) }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        val q = query.trim().lowercase()
        val state = _uiState.value
        when (state.selectedTabIndex) {
            0 -> {
                val filtered =
                    if (q.isEmpty()) state.allPendingPayments
                    else state.allPendingPayments.filter {
                        it.paymentName.lowercase().contains(q) ||
                                it.username.lowercase().contains(q) ||
                                it.dateOfPayment.lowercase().contains(q)
                    }
                _uiState.update { it.copy(pendingPayments = filtered) }
            }
            1 -> {
                val filtered =
                    if (q.isEmpty()) state.allPaidPayments
                    else state.allPaidPayments.filter {
                        it.paymentName.lowercase().contains(q) ||
                                it.username.lowercase().contains(q) ||
                                it.dateOfPayment.lowercase().contains(q)
                    }
                _uiState.update { it.copy(paidPayments = filtered) }
            }
            2 -> {
                val filtered =
                    if (q.isEmpty()) state.allFinesList
                    else state.allFinesList.filter { fine ->
                        fine.userName.lowercase().contains(q) ||
                                fine.fineDetails.any { it.name.lowercase().contains(q) }
                    }
                _uiState.update { it.copy(fines = filtered) }
            }
        }
    }

    // Abre diálogo precargando datos
    fun onFineEditClick(
        fineId: String,
        userId: String,
        reason: String,
        amount: Float
    ) {
        _uiState.update {
            it.copy(
                isEditDialogVisible = true,
                editFineId = fineId,
                editUserId = userId,
                editReason = reason,
                editAmountText = amount.toString(),
                editErrorMessage = null
            )
        }
    }

    fun onAmountChanged(newText: String) {
        _uiState.update { it.copy(editAmountText = newText) }
    }

    fun onCloseEditDialog() {
        _uiState.update {
            it.copy(
                isEditDialogVisible = false,
                editFineId = null,
                editUserId = null,
                editReason = "",
                editAmountText = "",
                editErrorMessage = null
            )
        }
    }

    // Botón Eliminar: amount=0 y status=PAID
    fun onDeleteFine() {
        val state = _uiState.value
        val fineId = state.editFineId ?: return
        val userId = state.editUserId ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmittingEdit = true, editErrorMessage = null) }
            try {
                repository.editFine(
                    fineId = fineId,
                    newAmount = 0f,
                    newMotive = null,
                    newStatus = app.cooperativa.graphql.type.FineStatus.PAID
                )

                // Como la lista muestra solo UNPAID, removemos la multa del usuario
                val updatedAll = state.allFinesList.map { user ->
                    if (user.userId == userId) {
                        user.copy(fineDetails = user.fineDetails.filterNot { it.id == fineId })
                    } else user
                }.filter { it.fineDetails.isNotEmpty() }

                _uiState.update {
                    it.copy(
                        allFinesList = updatedAll,
                        fines = updatedAll, // si estás viendo tab "Multas"
                        isSubmittingEdit = false,
                        isEditDialogVisible = false,
                        showConfetti = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isSubmittingEdit = false, editErrorMessage = e.message)
                }
            }
        }
    }

    // Botón Actualizar: mantiene UNPAID y cambia amount
    fun onUpdateFine() {
        val state = _uiState.value
        val fineId = state.editFineId ?: return
        val userId = state.editUserId ?: return

        // Validación simple del monto
        val amount = state.editAmountText.trim().replace(",", ".").toFloatOrNull()
        if (amount == null || amount < 0f) {
            _uiState.update { it.copy(editErrorMessage = "Monto inválido") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmittingEdit = true, editErrorMessage = null) }
            try {
                repository.editFine(
                    fineId = fineId,
                    newAmount = amount,
                    newMotive = null,
                    newStatus = FineStatus.UNPAID
                )

                // Actualiza monto en la lista
                val updatedAll = state.allFinesList.map { user ->
                    if (user.userId == userId) {
                        user.copy(
                            fineDetails = user.fineDetails.map { d ->
                                if (d.id == fineId) d.copy(amount = amount) else d
                            }
                        )
                    } else user
                }

                _uiState.update {
                    it.copy(
                        allFinesList = updatedAll,
                        fines = updatedAll,
                        isSubmittingEdit = false,
                        isEditDialogVisible = false,
                        showConfetti = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isSubmittingEdit = false, editErrorMessage = e.message)
                }
            }
        }
    }

    fun onConfettiFinished() {
        _uiState.update { it.copy(showConfetti = false) }
    }
}