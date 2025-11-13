package app.cooperativa.presentation.mainflow.directiva.manager.loan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.domain.directiva.DLoanManagerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DLoanManagerViewModel(
    private val repository: DLoanManagerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DLoanManagerState())
    val uiState = _uiState.asStateFlow()

    init { loadData() }

    fun loadData() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                val members = repository.getAllAffiliates()
                _uiState.update {
                    it.copy(
                        memberOptions = members,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Ooops! No se han podido obtener los socios")
                }
            }
        }
    }

    fun updateAffiliate(name: String, userId: String) {
        _uiState.update { it.copy(affiliateName = name, affiliateId = userId) }
    }

    fun updateLoanReason(reason: String) {
        val sanitized = reason.replace("\n", " ").replace("\r", " ").take(20)
        _uiState.update { it.copy(loanReason = sanitized) }
    }

    fun updateAmount(text: String) {
        val clean = sanitizeDecimal(text, 2)
        val parsed = clean.toFloatOrNull() ?: 0f
        _uiState.update { it.copy(amountText = clean, amount = parsed) }
    }

    fun updateInterest(text: String) {
        val clean = sanitizeDecimal(text, 2)
        val parsed = clean.toFloatOrNull() ?: 0f
        _uiState.update { it.copy(interestText = clean, interest = parsed) }
    }

    fun updateTotalQuota(text: String) {
        val digits = text.filter { it.isDigit() }.take(3) // tope sensato
        val parsed = digits.toIntOrNull() ?: 0
        _uiState.update { it.copy(totalQuotaText = digits, totalQuota = parsed) }
    }

    private fun sanitizeDecimal(input: String, maxDecimals: Int): String {
        val normalized = input.replace(',', '.')
        var dotSeen = false
        var decimals = 0
        return buildString {
            normalized.forEach { ch ->
                when {
                    ch.isDigit() -> {
                        if (dotSeen) {
                            if (decimals < maxDecimals) {
                                append(ch); decimals++
                            }
                        } else append(ch)
                    }
                    ch == '.' && !dotSeen -> {
                        append('.'); dotSeen = true; decimals = 0
                    }
                }
            }
        }
    }

    private fun isFormValid(state: DLoanManagerState): Boolean {
        return state.affiliateId.isNotBlank() &&
                state.loanReason.isNotBlank() &&
                state.amount > 0f &&
                state.interest >= 0f &&
                state.totalQuota > 0
    }

    fun submitLoan() {
        val s = _uiState.value
        if (!isFormValid(s)) {
            _uiState.update { it.copy(errorMessage = "Completa los campos requeridos") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                repository.submitLoan(
                    affiliateKey = s.affiliateId,
                    totalQuota = s.totalQuota,
                    baseNeededPayment = s.amount,
                    interestRate = s.interest, // 12.00 => 12%
                    reason = s.loanReason
                )
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message ?: "Error al crear el préstamo") }
            }
        }
    }
}