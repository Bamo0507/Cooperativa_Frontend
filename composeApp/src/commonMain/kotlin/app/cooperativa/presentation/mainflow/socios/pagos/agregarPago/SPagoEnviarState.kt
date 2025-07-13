package app.cooperativa.presentation.mainflow.socios.pagos.agregarPago

import app.cooperativa.data.model.dto.BasicUserInfo
import app.cooperativa.data.model.dto.CapitalContribution
import app.cooperativa.data.model.dto.FinePayAffiliate
import app.cooperativa.data.model.dto.LoanQuota
import app.cooperativa.data.model.dto.QuotaAffiliate

data class SPagoEnviarState(
    val nombrePago: String = "",
    val montoPago: Float = 0.0f,
    val numberoCuenta: String = "",
    val numeroBoleta: String = "",

    val cuotasDisponibles: List<QuotaAffiliate> = emptyList(),
    val prestamosDisponibles: List<LoanQuota> = emptyList(),
    val multasDisponibles: List<FinePayAffiliate> = emptyList(),
    val usuariosDisponibles: List<BasicUserInfo> = emptyList(),
    val aportesCapital: List<CapitalContribution> = emptyList(),

    val montoActualDeclarado: Float = 0.0f,

    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val errorMontoPago: Boolean = false,

    val selectedCuotas: List<QuotaAffiliate> = emptyList(),
    val selectedLoanQuotas: List<LoanQuota> = emptyList(),
    val selectedFines: List<FinePayAffiliate> = emptyList(),
)
