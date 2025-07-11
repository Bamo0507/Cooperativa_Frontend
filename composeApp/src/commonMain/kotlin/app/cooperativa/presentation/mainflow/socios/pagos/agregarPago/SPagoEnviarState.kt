package app.cooperativa.presentation.mainflow.socios.pagos.agregarPago

import app.cooperativa.data.model.dto.CapitalContribution
import app.cooperativa.data.model.dto.FinePayAffiliate
import app.cooperativa.data.model.dto.LoanQuota
import app.cooperativa.data.model.dto.QuotaAffiliate

data class SPagoEnviarState(
    val nombrePago: String = "",
    val montoPago: Float = 0.0f,
    val numberoCuenta: String = "",
    val numeroBoleta: String = "",
    val pagosCuota: List<QuotaAffiliate> = emptyList(),
    val pagosPrestamoCuota: List<LoanQuota> = emptyList(),
    val pagosMultas: List<FinePayAffiliate> = emptyList(),
    val aportesCapital: List<CapitalContribution> = emptyList(),

    val montoActualDeclarado: Float = 0.0f,

    val isLoading: Boolean = false,
    val errorMessage: String? = null
)