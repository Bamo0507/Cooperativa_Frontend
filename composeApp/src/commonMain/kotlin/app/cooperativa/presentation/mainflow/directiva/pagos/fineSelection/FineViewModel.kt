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
    private val userId: Int
): ViewModel() {
    private val _uiState = MutableStateFlow(FineSelectionState())
    val uiState: StateFlow<FineSelectionState> = _uiState.asStateFlow()

    init {
        loadFines()
    }

    fun loadFines() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        try {
            val fine = repository.getFinesForUserById(userId)
            val detailsUi = fine.fineDetails.map { d ->
                FineDetailUiState(
                    id = d.id,
                    name = d.name,
                    date = d.date,
                    amount = d.amount.toString(),
                    type = d.type
                )
            }
            _uiState.update {
                it.copy(
                    userName = fine.userName,
                    fineDetails = detailsUi,
                    isLoading = false
                )
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(errorMessage = e.message, isLoading = false) }
        }
    }

    fun onAmountChange(detailId: Int, newAmount: String) =
        _uiState.update { state ->
            state.copy(
                fineDetails = state.fineDetails.map {
                    if (it.id == detailId) it.copy(amount = newAmount) else it
                }
            )
        }

    fun onConfirmClick(onFinished: () -> Unit) = viewModelScope.launch {
        _uiState.update { it.copy(isSaving = true) }
        try {
            // TODO: call repository.updateFineDetail or batch update
            onFinished()
        } catch (e: Exception) {
            _uiState.update { it.copy(errorMessage = e.message, isSaving = false) }
        }
    }
}