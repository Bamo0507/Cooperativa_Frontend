package app.cooperativa.data.localdb

import kotlinx.datetime.LocalDate
import app.cooperativa.data.model.dto.Prestamo
import app.cooperativa.data.model.dto.Codeudor
import app.cooperativa.data.model.dto.PrestamoDetalle
import app.cooperativa.data.model.dto.Pagare
import app.cooperativa.data.model.dto.Estados

object PrestamoMockData {
    private val mockPrestamos = listOf(
        Prestamo(
            idPrestamo = 1,
            nombre = "Préstamo Vivienda",
            montoTotal = 10000.0f,
            montoCancelado = 10000.0f,
            motivo = "Compra Casa",
            estado = Estados.APROBADO,
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
            },
            pagare = listOf(
                Pagare(
                    pagare = "POR-2024-0001",
                    estado = Estados.APROBADO,
                    comentariosRechazo = null
                )
            )
        ),
        Prestamo(
            idPrestamo = 2,
            nombre = "Préstamo Vehículo",
            montoTotal = 5000.0f,
            montoCancelado = 1500.0f,
            motivo = "Compra Auto",
            estado = Estados.APROBADO,
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
            mensualidadesPrestamo = (1..24).map { month ->
                PrestamoDetalle(
                    numeroCuota = month,
                    montoCuota = 208.33f,
                    fechaVencimiento = LocalDate(2024, (month % 12).let { if (it == 0) 12 else it }, 10),
                    montoPagado = if (month <= 6) 208.33f else 0.0f,
                    multa = if (month == 7) 10.0f else 0.0f
                )
            },
            pagare = listOf(
                Pagare(
                    pagare = "POR-2024-0002",
                    estado = Estados.APROBADO,
                    comentariosRechazo = null
                )
            )
        ),
        Prestamo(
            idPrestamo = 3,
            nombre = "Préstamo Estudiantil",
            montoTotal = 8000.0f,
            montoCancelado = 0.0f,
            motivo = "Matrícula Universitaria",
            estado = Estados.PENDIENTE,
            tasaInteres = 4.0f,
            fechaSolicitud = LocalDate(2025, 2, 1),
            plazoMeses = 10,
            mesesCancelados = 0,
            codeudores = emptyList(),
            mensualidadesPrestamo = (1..10).map { month ->
                PrestamoDetalle(
                    numeroCuota = month,
                    montoCuota = 800.0f,
                    fechaVencimiento = LocalDate(2025, month, 1),
                    montoPagado = 0.0f,
                    multa = 0.0f
                )
            },
            pagare = listOf(
                Pagare(
                    pagare = "POR-2025-0003",
                    estado = Estados.PENDIENTE,
                    comentariosRechazo = null
                )
            )
        ),
        Prestamo(
            idPrestamo = 4,
            nombre = "Préstamo Emergencia",
            montoTotal = 2000.0f,
            montoCancelado = 0.0f,
            motivo = "Emergencia Médica",
            estado = Estados.RECHAZADO,
            tasaInteres = 6.0f,
            fechaSolicitud = LocalDate(2025, 3, 5),
            plazoMeses = 6,
            mesesCancelados = 0,
            codeudores = listOf(
                Codeudor(
                    nombre = "Ana Ruiz",
                    correo = "ana.ruiz@mail.com",
                    dpi = "1122334455667",
                    nit = "1122-334455-667-8",
                    direccion = "Zona 3",
                    telefono = "55533445"
                )
            ),
            mensualidadesPrestamo = emptyList(),
            pagare = listOf(
                Pagare(
                    pagare = "POR-2025-0004",
                    estado = Estados.RECHAZADO,
                    comentariosRechazo = "Ingresos insuficientes"
                )
            )
        )
    )

    fun getAllPrestamos(): List<Prestamo> = mockPrestamos
}
