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
            estadoPrestamo = Estados.ON_REVISION,
            linkDescargaPagare = ""
        ),
        PrestamosStatus(
            solicitudId = 2,
            solicitanteId = 2,
            prestamoNombre = "Moto Yamaha",
            estadoPrestamo = Estados.ACCEPTED,
            linkDescargaPagare = ""
        ),
        PrestamosStatus(
            solicitudId = 3,
            solicitanteId = 3,
            prestamoNombre = "Casa de Playa",
            estadoPrestamo = Estados.ON_REVISION,
            linkDescargaPagare = ""
        )
    )

    private val mockPagaresStatus = listOf(
        PagaresStatus(
            solicitudId = 1,
            solicitanteId = 1,
            prestamoNombre = "Negocio de Banano",
            estadoPagare = Estados.ON_REVISION,
        ),
        PagaresStatus(
            solicitudId = 2,
            solicitanteId = 2,
            prestamoNombre = "Moto BMW",
            estadoPagare = Estados.ACCEPTED,
        ),
        PagaresStatus(
            solicitudId = 3,
            solicitanteId = 3,
            prestamoNombre = "Casa de Puerto",
            estadoPagare = Estados.ON_REVISION,
        )
    )

    fun getPrestamosStatus(userId: Int): List<PrestamosStatus> {
        return mockPrestamoStatus
    }

    fun getPagaresStatus(userId: Int): List<PagaresStatus>{
        return mockPagaresStatus
    }
}