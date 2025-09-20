package app.cooperativa.data.localdb.socios

import app.cooperativa.data.model.dto.Codeudor
import app.cooperativa.data.model.dto.Estados
import app.cooperativa.data.model.dto.Prestamo
import app.cooperativa.data.model.dto.PrestamoDetalle
import kotlinx.datetime.LocalDate

object SPrestamoMockData {
    private val mockPrestamos = listOf(
        Prestamo(
            idPrestamo = 1,
            nombreSolicitante = "Laura Martínez",
            nombre = "Préstamo Vivienda",
            montoTotal = 10000.0f,
            montoCancelado = 10000.0f,
            motivo = "Compra Casa",
            estado = Estados.COMPLETED,
            tasaInteres = 5.0f,
            fechaSolicitud = LocalDate(2024, 1, 15),
            plazoMeses = 12,
            mesesCancelados = 12,
            codeudores = listOf(
                Codeudor(
                    nombre = "Carlos Pérez",
                    correo = "carlos.perez@mail.com",
                    dpi = "1234567890101",
                    nit = "1234-567890-123-4",
                    direccion = "Zona 1",
                    telefono = "55541234"
                )
            ),
            mensualidadesPrestamo = (1..12).map { month ->
                PrestamoDetalle(
                    numeroCuota = month,
                    montoCuota = 833.33f,
                    fechaVencimiento = LocalDate(2024, month, 15),
                    montoPagado = 833.33f,
                    multa = 0.0f
                )
            }
        ),
        Prestamo(
            idPrestamo = 2,
            nombreSolicitante = "Laura Martínez",
            nombre = "Préstamo Vehículo",
            montoTotal = 5000.0f,
            montoCancelado = 1500.0f,
            motivo = "Compra Auto",
            estado = Estados.COMPLETED,
            tasaInteres = 7.5f,
            fechaSolicitud = LocalDate(2024, 6, 10),
            plazoMeses = 24,
            mesesCancelados = 6,
            codeudores = listOf(
                Codeudor(
                    nombre = "Lucía Gómez",
                    correo = "lucia.gomez@mail.com",
                    dpi = "1098765432109",
                    nit = "9876-543210-987-5",
                    direccion = "Zona 5",
                    telefono = "55598765"
                ),
                Codeudor(
                    nombre = "Miguel Santos",
                    correo = "miguel.santos@mail.com",
                    dpi = "1987654321098",
                    nit = "8765-432109-876-3",
                    direccion = "Zona 10",
                    telefono = "55587654"
                )
            ),
            mensualidadesPrestamo = emptyList()
        )
    )

    //userId not used, will be when using backend
    fun getPrestamosByUser(userId: Int): List<Prestamo> {
        return mockPrestamos
    }
}