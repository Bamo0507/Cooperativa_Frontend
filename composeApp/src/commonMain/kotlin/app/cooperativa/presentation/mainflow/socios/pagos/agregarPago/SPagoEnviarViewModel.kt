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
import app.cooperativa.domain.localstorage.PreferencesLocalStorage
import app.cooperativa.domain.share.convertHeicToJpeg
import com.mohamedrejeb.calf.core.PlatformContext
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.io.getPath
import com.mohamedrejeb.calf.io.readByteArray
import kotlinx.coroutines.flow.update

class SPagoEnviarViewModel(
    private val repository: SPagoEnviarRepository,
    private val prefs: PreferencesLocalStorage,
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

    fun updateMontoPago(amountText: String) {
        val normalized = amountText.replace(',', '.')
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
                        if (isEmpty()) append('0')
                        append('.')
                        dotSeen = true
                        decimals = 0
                    }
                }
            }
        }
        val parsed = clean.toFloatOrNull() ?: 0f
        _uiState.update {
            it.copy(
                montoPagoText = clean,
                montoPago = parsed,
                montoActualDeclarado = parsed
            )
        }
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
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                // Flag para UI
                val hasSentPayment = prefs.hasSentPayment()

                // Acceso
                val accessToken = prefs.getAccessToken().orEmpty()

                // Datos mock/actuales (cuotas, préstamos, usuarios)
                val cuotas = repository.getCuotasMensualesPendientes()
                val prestamos = repository.getPrestamoCuotasByUser(1) // TODO: migrar a accessToken cuando tengas el query
                val usuarios = repository.getAllUsers()

                // Multas desde GraphQL (/graphql/fine) con fallback a vacío si falla
                val multas = try {
                    if (accessToken.isNotBlank()) {
                        repository.getFinesByAccessToken(accessToken)
                    } else {
                        emptyList()
                    }
                } catch (e: Exception) {
                    // log si quieres
                    emptyList()
                }

                // Set UI
                _uiState.update {
                    it.copy(
                        hasSentPayment = hasSentPayment,
                        cuotasDisponibles = cuotas,
                        prestamosDisponibles = prestamos,
                        multasDisponibles = multas,
                        usuariosDisponibles = usuarios,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                // Error general
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Error al cargar datos" ?: "Error al cargar datos")
                }
            }
        }
    }

    fun handleImagePicked(ctx: PlatformContext, image: KmpFile) {
        viewModelScope.launch {
            val path = image.getPath(ctx) ?: ""
            val isHeic = path.endsWith(".heic", ignoreCase = true) || path.endsWith(".heif", ignoreCase = true)

            val originalBytes = image.readByteArray(ctx)

            val processedBytes = if (isHeic) {
                convertHeicToJpeg(originalBytes)
            } else {
                originalBytes
            }

            _uiState.update {
                it.copy(
                    bytesImagen = processedBytes
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
