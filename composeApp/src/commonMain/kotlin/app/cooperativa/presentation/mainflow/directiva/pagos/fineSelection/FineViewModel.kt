package app.cooperativa.presentation.mainflow.directiva.pagos.fineSelection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import app.cooperativa.domain.directiva.DFinesRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class FineViewModel(
    private val repository: DFinesRepository,
    private val accessKey: String
): ViewModel() {

    private val _uiState = MutableStateFlow(FineSelectionState())
    val uiState: StateFlow<FineSelectionState> = _uiState.asStateFlow()

    init { loadFines() }

    fun loadFines() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        try {
            val fines = repository.getFinesByAccessKey(accessKey)
            val unpaid = fines.filter { it.status.rawValue == "UNPAID" }
            val details = unpaid.map { f ->
                FineDetailUiState(
                    id = f.id,
                    name = f.reason,
                    amount = f.amount.toString()
                )
            }
            _uiState.update { it.copy(fineDetails = details, isLoading = false) }
        } catch (e: Exception) {
            _uiState.update { it.copy(errorMessage = e.message, isLoading = false) }
        }
    }

    fun onAmountChange(fineKey: String, newAmount: String) =
        _uiState.update { state ->
            state.copy(
                fineDetails = state.fineDetails.map {
                    if (it.id == fineKey) it.copy(amount = newAmount) else it
                }
            )
        }

    fun onConfirmClick(onFinished: () -> Unit) = viewModelScope.launch {
        _uiState.update { it.copy(isSaving = true, errorMessage = null) }
        try {
            // Para cada multa, enviar editFine con status según regla (0 => PAID)
            uiState.value.fineDetails.forEach { d ->
                val amount = d.amount.toFloatOrNull() ?: 0f
                val status = if (amount == 0f) app.cooperativa.graphql.type.FineStatus.PAID
                else app.cooperativa.graphql.type.FineStatus.UNPAID

                repository.editFine(
                    fineKey   = d.id,
                    newAmount = amount,
                    newMotive = d.name,
                    newStatus = status
                )
            }
            _uiState.update { it.copy(isSaving = false) }
            onFinished()
        } catch (e: Exception) {
            _uiState.update { it.copy(isSaving = false, errorMessage = e.message) }
        }
    }
}