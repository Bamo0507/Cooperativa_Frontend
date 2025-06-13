package app.cooperativa.data.localdb

import app.cooperativa.data.model.dto.Estados
import app.cooperativa.data.model.dto.Pagare

object PagaresMockData {
    private val mockPagares = listOf(
        Pagare(
            idPagare = 1,
            solicitante = "Juan Pérez",
            nombrePrestamo = "Préstamo Vivienda",
            pagareKey = "PG-001",
            estado = Estados.PENDIENTE,
            comentariosRechazo = null
        ),
        Pagare(
            idPagare = 2,
            solicitante = "María López",
            nombrePrestamo = "Préstamo Auto",
            pagareKey = "PG-002",
            estado = Estados.APROBADO,
            comentariosRechazo = null
        ),
        Pagare(
            idPagare = 3,
            solicitante = "Carlos Sánchez",
            nombrePrestamo = "Préstamo Educativo",
            pagareKey = "PG-003",
            estado = Estados.RECHAZADO,
            comentariosRechazo = "Documentación incompleta"
        ),
        Pagare(
            idPagare = 4,
            solicitante = "Ana Torres",
            nombrePrestamo = "Préstamo Personal",
            pagareKey = "PG-004",
            estado = Estados.PENDIENTE,
            comentariosRechazo = null
        ),
        Pagare(
            idPagare = 5,
            solicitante = "Luis Gómez",
            nombrePrestamo = "Préstamo PyME",
            pagareKey = "PG-005",
            estado = Estados.PENDIENTE,
            comentariosRechazo = null
        )
    )

    fun getAllPagares(): List<Pagare> = mockPagares

    fun getPendingPagares(): List<Pagare> = mockPagares.filter { it.estado == Estados.PENDIENTE }

    fun getPagareById(id: Int): Pagare? = mockPagares.find { it.idPagare == id }
}