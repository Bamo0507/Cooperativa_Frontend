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

    fun submitFine(onSuccess: (() -> Unit)? = null) {
        val state = _uiState.value

        // Validaciones basicas
        when {
            state.affiliateId.isBlank() -> {
                _uiState.update { it.copy(errorMessage = "Selecciona un asociado.") }
                return
            }
            state.fineName.isBlank() -> {
                _uiState.update { it.copy(errorMessage = "Ingresa la razón de la multa.") }
                return
            }
            state.fineAmount <= 0f -> {
                _uiState.update { it.copy(errorMessage = "El monto debe ser mayor a 0.") }
                return
            }
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                repository.submitFine(
                    affiliateKey = state.affiliateId,
                    amount = state.fineAmount,
                    motive  = state.fineName
                )
                _uiState.update { it.copy(isLoading = false) }
                onSuccess?.invoke()
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message ?: "Error al crear la multa") }
            }
        }
    }

    fun updateFineName(name: String) {
        val sanitized = name
            .replace("\n", " ")
            .replace("\r", " ")
            .take(20)
        _uiState.update { it.copy(
            fineName = sanitized
        ) }
    }

    fun updateFineAmount(amount: String) {
        val normalized = amount.replace(',', '.')
        var dotSeen = false
        var decimals = 0
        val clean = buildString {
            normalized.forEach { ch ->
                when {
                    ch.isDigit() -> {
                        if (dotSeen) {
                            if (decimals < 2) {
                                append(ch)
                                decimals++
                            }
                        } else {
                            append(ch)
                        }
                    }
                    ch == '.' && !dotSeen -> {
                        append(ch)
                        dotSeen = true
                        decimals = 0
                    }
                }
            }
        }
        val parsed = clean.toFloatOrNull() ?: 0f
        _uiState.update { it.copy(
            fineAmountText = clean,
            fineAmount = parsed
        ) }
    }

    fun updateAffiliate(member: String, userId: String) {
        _uiState.value = _uiState.value.copy(
            affiliateName = member,
            affiliateId = userId
        )
    }
}
