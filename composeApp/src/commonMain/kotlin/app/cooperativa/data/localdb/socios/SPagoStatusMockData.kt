package app.cooperativa.data.localdb.socios

import app.cooperativa.data.model.dto.Estados
import app.cooperativa.data.model.dto.PagosStatus

object SPagoStatusMockData {
    private val mockPagos = listOf(
        PagosStatus(
            pagoId = "1",
            nombrePago="Pago Febrero Cuotas 2023",
            dateOfPayment = "2023-02-01",
            estado = Estados.COMPLETED
        ),
        PagosStatus(
            pagoId = "2",
            nombrePago="Pago Enero Cuotas 2023",
            dateOfPayment = "2023-01-01",
            estado = Estados.ON_REVISION
        ),
        PagosStatus(
            pagoId = "3",
            nombrePago="Pago Diciembre Cuotas 2022",
            dateOfPayment = "2022-12-01",
            estado = Estados.PENDING
        )
    )

    fun getPagosStatusByUser(userId: Int): List<PagosStatus> {
        return mockPagos
    }
}