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
import app.cooperativa.domain.share.convertToJpeg
import app.cooperativa.graphql.type.PayedToInput
import com.mohamedrejeb.calf.core.PlatformContext
import com.mohamedrejeb.calf.io.KmpFile
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
        _uiState.update { state ->
            state.copy(
                selectedCuotas = state.selectedCuotas + cuota,
                cuotasDisponibles = state.cuotasDisponibles - cuota
            )
        }
    }

    fun addLoanQuota(loan: LoanQuota) {
        _uiState.update { state ->
            state.copy(
                selectedLoanQuotas = state.selectedLoanQuotas + loan,
                prestamosDisponibles = state.prestamosDisponibles - loan
            )
        }
    }

    fun addFine(fine: FinePayAffiliate) {
        _uiState.update { state ->
            state.copy(
                selectedFines = state.selectedFines + fine,
                multasDisponibles = state.multasDisponibles - fine
            )
        }
    }

    fun addCapitalContribution(user: BasicUserInfo, amount: Float) {
        val contribution = CapitalContribution(
            userId = user.userId,
            userName = user.name,
            amount = amount
        )
        _uiState.update { state ->
            state.copy(
                aportesCapital = state.aportesCapital + contribution,
                // quitar usuario de opciones para evitar duplicado
                usuariosDisponibles = state.usuariosDisponibles.filter { it.userId != user.userId }
            )
        }
    }

    fun removeCuota(cuota: QuotaAffiliate) {
        _uiState.update { state ->
            state.copy(
                selectedCuotas = state.selectedCuotas - cuota,
                // devolver la cuota a disponibles
                cuotasDisponibles = state.cuotasDisponibles + cuota
            )
        }
    }
    fun removeLoanQuota(loan: LoanQuota) {
        _uiState.update { state ->
            state.copy(
                selectedLoanQuotas = state.selectedLoanQuotas - loan,
                prestamosDisponibles = state.prestamosDisponibles + loan
            )
        }
    }
    fun removeFine(fine: FinePayAffiliate) {
        _uiState.update { state ->
            state.copy(
                selectedFines = state.selectedFines - fine,
                multasDisponibles = state.multasDisponibles + fine
            )
        }
    }
    fun removeCapitalContribution(aporte: CapitalContribution) {
        _uiState.update { state ->
            // reconstruimos una opción mínima para devolver al dropdown
            val restoredUser = BasicUserInfo(userId = aporte.userId, name = aporte.userName)
            state.copy(
                aportesCapital = state.aportesCapital - aporte,
                usuariosDisponibles = state.usuariosDisponibles + restoredUser
            )
        }
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val hasSentPayment = prefs.hasSentPayment()
//                val accessToken = prefs.getAccessToken().orEmpty()
                //TODO: remove until backend fixes problems
                val accessToken = "51AD3720C2EBE517575CF9C3E74A61A5F78A6F08B28B8CA5692830633C9665B2"

                // Si no hay token, dejamos todo vacío
                if (accessToken.isBlank()) {
                    _uiState.update {
                        it.copy(
                            hasSentPayment = hasSentPayment,
                            cuotasDisponibles = emptyList(),
                            prestamosDisponibles = emptyList(),
                            multasDisponibles = emptyList(),
                            usuariosDisponibles = emptyList(),
                            isLoading = false,
                            errorMessage = "No hay token de acceso"
                        )
                    }
                    return@launch
                }

                // Cargas reales
                val cuotas = repository.getMonthlyAffiliateQuota(accessToken)
                val prestamos = repository.getPendingLoansQuotas(accessToken)
                val multas = repository.getFinesByAccessToken(accessToken)

                _uiState.update { current ->
                    current.copy(
                        hasSentPayment = hasSentPayment,
                        // Filtra lo ya seleccionado para evitar duplicados visuales
                        cuotasDisponibles = cuotas.filter { it !in current.selectedCuotas },
                        prestamosDisponibles = prestamos.filter { it !in current.selectedLoanQuotas },
                        multasDisponibles = multas.filter { it !in current.selectedFines },
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message ?: "Error al cargar datos")
                }
            }
        }
    }

    fun handleImagePicked(ctx: PlatformContext, image: KmpFile) {
        viewModelScope.launch {
            val original = image.readByteArray(ctx)
            val jpeg = convertToJpeg(original) // SIEMPRE JPEG
            _uiState.update { it.copy(bytesImagen = jpeg) }
        }
    }

    fun submitPayment() {
        viewModelScope.launch {
            val ok = validateDeclaredAmount()
            if (!ok) return@launch

            val image = _uiState.value.bytesImagen
            if (image == null || image.isEmpty()) {
                _uiState.update { it.copy(errorMessage = "Adjunta una imagen del comprobante") }
                return@launch
            }

            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val accessToken = prefs.getAccessToken().orEmpty()

                val ticketId = repository.uploadTicket(accessToken, image)

                val payedItems = buildBeingPayed(_uiState.value)

                repository.createUserPayment(
                    accessToken = accessToken,
                    comprobantePath = ticketId,
                    name = _uiState.value.nombrePago.ifBlank { "Pago" },
                    totalAmount = _uiState.value.montoActualDeclarado,
                    ticketNumber = _uiState.value.numeroBoleta,
                    accountNumber = _uiState.value.numberoCuenta,
                    beingPayed = payedItems,
                )

                _uiState.update { it.copy(isLoading = false, paymentSentSuccesffully = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message ?: "Error enviando pago") }
            }
        }
    }


    private fun buildBeingPayed(state: SPagoEnviarState): List<PayedToInput> {
        val fromCuotas = state.selectedCuotas.map { c ->
            PayedToInput(
                modelKey = c.idCuota.toString(),
                modelType = "QUOTA",
                amount = c.montoCuota.toDouble()
            )
        }
        val fromLoans = state.selectedLoanQuotas.map { l ->
            PayedToInput(
                modelKey = l.id.toString(),
                modelType = "LOAN",
                amount = l.monto.toDouble()
            )
        }
        val fromFines = state.selectedFines.map { f ->
            PayedToInput(
                modelKey = f.id,
                modelType = "FINE",
                amount = f.fineAmount.toDouble()
            )
        }
        val fromCapital = state.aportesCapital.map { a ->
            PayedToInput(
                modelKey = a.userId.toString(),
                modelType = "QUOTA",
                amount = a.amount.toDouble()
            )
        }
        return fromCuotas + fromLoans + fromFines + fromCapital
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
