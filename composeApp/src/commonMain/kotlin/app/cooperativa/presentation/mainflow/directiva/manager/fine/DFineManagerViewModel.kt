package app.cooperativa.presentation.mainflow.directiva.manager.fine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.domain.directiva.DFineManagerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DFineManagerViewModel(
    private val repository: DFineManagerRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<DFineManagerState> = MutableStateFlow(
        DFineManagerState()
    )
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            try {
                _uiState.update { state ->
                    state.copy(
                        isLoading = true,
                        errorMessage = null
                    )
                }
                val members = repository.getAllAffiliates()
                _uiState.update { state ->
                    state.copy(
                        memberOptions = members,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = "Ooops! No se han podido obtener los socios"
                    )
                }
            }
        }
    }

    fun updateFineName(name: String) {
        _uiState.value = _uiState.value.copy(
            fineName = name
        )
    }

    fun updateFineAmount(amount: String) {
        // sanitize and parse safely (supports one decimal point)
        val clean = buildString {
            var dotSeen = false
            for (ch in amount.replace(',', '.')) {
                if (ch.isDigit()) append(ch)
                else if (ch == '.' && !dotSeen) {
                    append(ch)
                    dotSeen = true
                }
            }
        }
        val parsed = clean.toFloatOrNull() ?: 0f
        _uiState.update { it.copy(fineAmount = parsed) }
    }

    fun updateAffiliate(member: String, userId: Int) {
        _uiState.value = _uiState.value.copy(
            affiliateName = member,
            affiliateId = userId
        )
    }
}
