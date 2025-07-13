package app.cooperativa.presentation.mainflow.socios.pagos.agregarPago

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.data.model.dto.BasicUserInfo
import app.cooperativa.data.model.dto.FinePayAffiliate
import app.cooperativa.data.model.dto.LoanQuota
import app.cooperativa.data.model.dto.QuotaAffiliate
import app.cooperativa.domain.socios.SPagoEnviarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import app.cooperativa.data.model.dto.CapitalContribution

class SPagoEnviarViewModel(
    private val repository: SPagoEnviarRepository,
    private val userId: Int = 1 // TODO: Delete and use local storage inyection instead
) : ViewModel() {
    private val _uiState: MutableStateFlow<SPagoEnviarState> = MutableStateFlow(
        SPagoEnviarState()
    )
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun updateNombrePago(nombre: String) {
        _uiState.value = _uiState.value.copy(
            nombrePago = nombre.take(30)
        )
    }

    fun updateMontoPago(monto: Float) {
        _uiState.value = _uiState.value.copy(
            montoPago = monto
        )
    }

    fun updateNumeroCuenta(cuenta: String) {
        _uiState.value = _uiState.value.copy(
            numberoCuenta = cuenta.filter { it.isDigit() }
        )
    }

    fun updateNumeroBoleta(boleta: String) {
        _uiState.value = _uiState.value.copy(
            numeroBoleta = boleta.filter { it.isDigit() }
        )
    }

    fun addCuota(cuota: QuotaAffiliate) {
        _uiState.value = _uiState.value.copy(
            selectedCuotas = _uiState.value.selectedCuotas + cuota
        )
    }

    fun addLoanQuota(loan: LoanQuota) {
        _uiState.value = _uiState.value.copy(
            selectedLoanQuotas = _uiState.value.selectedLoanQuotas + loan
        )
    }

    fun addFine(fine: FinePayAffiliate) {
        _uiState.value = _uiState.value.copy(
            selectedFines = _uiState.value.selectedFines + fine
        )
    }

    fun addCapitalContribution(user: BasicUserInfo, amount: Float) {
        val contribution = CapitalContribution(
            userId = user.userId,
            userName = user.name,
            amount = amount
        )
        _uiState.value = _uiState.value.copy(
            aportesCapital = _uiState.value.aportesCapital + contribution
        )
    }

    fun removeCuota(cuota: QuotaAffiliate) {
        _uiState.value = _uiState.value.copy(
            selectedCuotas = _uiState.value.selectedCuotas - cuota
        )
    }
    fun removeLoanQuota(loan: LoanQuota) {
        _uiState.value = _uiState.value.copy(
            selectedLoanQuotas = _uiState.value.selectedLoanQuotas - loan
        )
    }
    fun removeFine(fine: FinePayAffiliate) {
        _uiState.value = _uiState.value.copy(
            selectedFines = _uiState.value.selectedFines - fine
        )
    }
    fun removeCapitalContribution(aporte: CapitalContribution) {
        _uiState.value = _uiState.value.copy(
            aportesCapital = _uiState.value.aportesCapital - aporte
        )
    }

    private fun loadData() {
        viewModelScope.launch {
            // Indica que se está cargando
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )
            try {
                // Carga de datos del repositorio
                val cuotas = repository.getCuotasMensualesPendientes()
                val prestamos = repository.getPrestamoCuotasByUser(userId)
                val multas = repository.getPagoMultasByQuotasUser(listOf(userId))
                val usuarios = repository.getAllUsers()

                // Actualiza el estado con los datos cargados
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    cuotasDisponibles = cuotas,
                    prestamosDisponibles = prestamos,
                    multasDisponibles = multas,
                    usuariosDisponibles = usuarios
                )
            } catch (e: Exception) {
                // Manejo de error
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }
    /**
     * Valida que la suma de todos los montos seleccionados coincida con el monto declarado.
     * Retorna true si coinciden, false en caso contrario y actualiza errorMontoPago.
     */
    fun validateDeclaredAmount(): Boolean {
        val state = _uiState.value

        // Suma montos de cuotas
        val sumCuotas = state.selectedCuotas.sumOf { it.montoCuota.toDouble() }

        // Suma montos de préstamos
        val sumPrestamos = state.selectedLoanQuotas.sumOf { it.monto.toDouble() }

        // Suma montos de multas
        val sumMultas = state.selectedFines.sumOf { it.fineAmount.toDouble() }

        // Suma aportes de capital
        val sumCapital = state.aportesCapital.sumOf { it.amount.toDouble() }

        val totalSelected = (sumCuotas + sumPrestamos + sumMultas + sumCapital).toFloat()
        return if (totalSelected == state.montoActualDeclarado) {
            _uiState.value = state.copy(errorMontoPago = false)
            true
        } else {
            _uiState.value = state.copy(errorMontoPago = true)
            false
        }
    }
}