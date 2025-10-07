package app.cooperativa.data.localdb.socios

import app.cooperativa.data.model.dto.BasicUserInfo
import app.cooperativa.data.model.dto.LoanQuota
import app.cooperativa.data.model.dto.QuotaAffiliate

object SPagoEnviarMockData {
    private val mockCuotas = listOf(
        QuotaAffiliate(
            idCuota = 1,
            idAsociado = "1",
            identifier = "Juan Pérez",
            montoCuota = 100f
        ),
        QuotaAffiliate(
            idCuota = 2,
            idAsociado = "2",
            identifier = "María López",
            montoCuota = 150f
        ),
        QuotaAffiliate(
            idCuota = 3,
            idAsociado = "3",
            identifier = "Carlos Ruiz",
            montoCuota = 120f
        )
    )

    private val mockLoanQuotas = listOf(
        LoanQuota(
            id = "1",
            nombrePago = "Préstamo Vivienda",
            monto = 1000f
        ),
        LoanQuota(
            id = "2",
            nombrePago = "Préstamo Vehículo",
            monto = 5000f
        )
    )

    private val mockUsers = listOf(
        BasicUserInfo(
            userId = 1,
            name = "Juan Pérez"
        ),
        BasicUserInfo(
            userId = 2,
            name = "María López"
        ),
        BasicUserInfo(
            userId = 3,
            name = "Carlos Ruiz"
        )
    )

    fun getCuotasMensualesPendientes(): List<QuotaAffiliate> =
        mockCuotas

    fun getPrestamoCuotasByUser(userId: Int): List<LoanQuota> =
        // De momento devolvemos todas las cuotas de préstamo sin filtrar
        mockLoanQuotas

    fun getAllUsers(): List<BasicUserInfo> =
        mockUsers
}