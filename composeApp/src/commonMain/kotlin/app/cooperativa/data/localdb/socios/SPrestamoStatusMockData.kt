package app.cooperativa.data.localdb.socios

import app.cooperativa.data.model.dto.Estados
import app.cooperativa.data.model.dto.PagaresStatus
import app.cooperativa.data.model.dto.PrestamosStatus

object SPrestamoStatusMockData {
    private val mockPrestamoStatus = listOf(
        PrestamosStatus(
            solicitudId = 1,
            solicitanteId = 1,
            prestamoNombre = "Negocio de Fresas",
            estadoPrestamo = Estados.PENDIENTE,
            linkDescargaPagare = ""
        ),
        PrestamosStatus(
            solicitudId = 2,
            solicitanteId = 2,
            prestamoNombre = "Moto Yamaha",
            estadoPrestamo = Estados.APROBADO,
            linkDescargaPagare = ""
        ),
        PrestamosStatus(
            solicitudId = 3,
            solicitanteId = 3,
            prestamoNombre = "Casa de Playa",
            estadoPrestamo = Estados.RECHAZADO,
            linkDescargaPagare = ""
        )
    )

    private val mockPagaresStatus = listOf(
        PagaresStatus(
            solicitudId = 1,
            solicitanteId = 1,
            prestamoNombre = "Negocio de Banano",
            estadoPagare = Estados.PENDIENTE,
        ),
        PagaresStatus(
            solicitudId = 2,
            solicitanteId = 2,
            prestamoNombre = "Moto BMW",
            estadoPagare = Estados.APROBADO,
        ),
        PagaresStatus(
            solicitudId = 3,
            solicitanteId = 3,
            prestamoNombre = "Casa de Puerto",
            estadoPagare = Estados.RECHAZADO,
        )
    )

    fun getPrestamosStatus(userId: Int): List<PrestamosStatus> {
        return mockPrestamoStatus
    }

    fun getPagaresStatus(userId: Int): List<PagaresStatus>{
        return mockPagaresStatus
    }
}