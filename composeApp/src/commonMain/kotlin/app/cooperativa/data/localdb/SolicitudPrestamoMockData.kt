package app.cooperativa.data.localdb

import kotlinx.datetime.LocalDate
import app.cooperativa.data.model.dto.SolicitudPrestamo
import app.cooperativa.data.model.dto.Codeudor
import app.cooperativa.data.model.ui.BasicInfoLoan

/**
 * Mock data para SolicitudPrestamo y funciones de acceso.
 */
object SolicitudPrestamoMockData {
    private val mockSolicitudes = listOf(
        SolicitudPrestamo(
            nombreSolicitante = "Laura Martínez",
            nombrePrestamo = "Préstamo Hogar",
            montoTotal = 15000.0f,
            plazoMeses = 12,
            motivo = "Consolidación de Deuda",
            codeudores = listOf(
                Codeudor(
                    nombre = "Carlos Pérez",
                    correo = "carlos.perez@mail.com",
                    dpi = "1234567890101",
                    nit = "1234-567890-123-4",
                    direccion = "Zona 1",
                    telefono = "55541234"
                ),
                Codeudor(
                    nombre = "María López",
                    correo = "maria.lopez@mail.com",
                    dpi = "1098765432109",
                    nit = "9876-543210-987-5",
                    direccion = "Zona 5",
                    telefono = "55598765"
                )
            ),
            comentarios = null
        ),
        SolicitudPrestamo(
            nombreSolicitante = "Mario López",
            nombrePrestamo = "Préstamo Moto",
            montoTotal = 5000.0f,
            plazoMeses = 24,
            motivo = "Compra Moto",
            codeudores = emptyList(),
            comentarios = "Requiere revisión adicional de ingresos"
        ),
        SolicitudPrestamo(
            nombreSolicitante = "Ana Pérez",
            nombrePrestamo = "Préstamo Emergencia",
            montoTotal = 8000.0f,
            plazoMeses = 6,
            motivo = "Emergencia Médica",
            codeudores = listOf(
                Codeudor(
                    nombre = "José Ramírez",
                    correo = "jose.ramirez@mail.com",
                    dpi = "1987654321098",
                    nit = "8765-432109-876-3",
                    direccion = "Zona 10",
                    telefono = "55587654"
                )
            ),
            comentarios = "Documentación incompleta"
        ),
        SolicitudPrestamo(
            nombreSolicitante = "Juan Gómez",
            nombrePrestamo = "Préstamo Auto",
            montoTotal = 20000.0f,
            plazoMeses = 36,
            motivo = "Expansión Negocio",
            codeudores = listOf(
                Codeudor(
                    nombre = "Lucía Fernández",
                    correo = "lucia.fernandez@mail.com",
                    dpi = "1122334455667",
                    nit = "1122-334455-667-8",
                    direccion = "Zona 3",
                    telefono = "55533445"
                )
            ),
            comentarios = null
        )
    )

    /**
     * Obtiene la lista de información básica de todas las solicitudes.
     */
    fun getAllBasicInfo(): List<BasicInfoLoan> = mockSolicitudes.mapIndexed { index, solicitud ->
        BasicInfoLoan(
            id = index + 1,
            loanName = solicitud.motivo,
            username = solicitud.nombreSolicitante
        )
    }

    /**
     * Busca una solicitud por su ID (1-based).
     * @return la SolicitudPrestamo correspondiente, o null si no existe.
     */
    fun getSolicitudById(id: Int): SolicitudPrestamo? =
        mockSolicitudes.getOrNull(id - 1)
}
