package app.cooperativa.data.localdb.socios

import app.cooperativa.data.model.dto.BasicUserInfo
import app.cooperativa.data.model.dto.FinePayAffiliate
import app.cooperativa.data.model.dto.LoanQuota
import app.cooperativa.data.model.dto.QuotaAffiliate

object SPagoEnviarMockData {
    private val mockCuotas = listOf(
        QuotaAffiliate(
            idCuota = 1,
            idAsociado = 1,
            nombreAsociado = "Juan Pérez",
            montoCuota = 100f
        ),
        QuotaAffiliate(
            idCuota = 2,
            idAsociado = 2,
            nombreAsociado = "María López",
            montoCuota = 150f
        ),
        QuotaAffiliate(
            idCuota = 3,
            idAsociado = 3,
            nombreAsociado = "Carlos Ruiz",
            montoCuota = 120f
        )
    )

    private val mockLoanQuotas = listOf(
        LoanQuota(
            id = 1,
            nombrePago = "Préstamo Vivienda",
            monto = 1000f
        ),
        LoanQuota(
            id = 2,
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

    private val mockFines = listOf(
        FinePayAffiliate(userId = 1, fineName = "Multa por atraso cuota #1", fineAmount = 10f),
        FinePayAffiliate(userId = 1, fineName = "Multa administrativa #1", fineAmount = 5f),
        FinePayAffiliate(userId = 2, fineName = "Multa por atraso cuota #2", fineAmount = 20f),
        FinePayAffiliate(userId = 2, fineName = "Multa administrativa #2", fineAmount = 10f),
        FinePayAffiliate(userId = 3, fineName = "Multa por atraso cuota #3", fineAmount = 30f)
    )

    fun getCuotasMensualesPendientes(): List<QuotaAffiliate> =
        mockCuotas

    fun getPrestamoCuotasByUser(userId: Int): List<LoanQuota> =
        // De momento devolvemos todas las cuotas de préstamo sin filtrar
        mockLoanQuotas

    fun getPagoMultasByQuotasUser(userIds: List<Int>): List<FinePayAffiliate> {
        return mockFines.filter { it.userId in userIds }
    }

    fun getAllUsers(): List<BasicUserInfo> =
        mockUsers
}