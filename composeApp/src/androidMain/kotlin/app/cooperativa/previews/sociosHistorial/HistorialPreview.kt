package app.cooperativa.previews.sociosHistorial

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.cooperativa.data.model.dto.Codeudor
import app.cooperativa.data.model.dto.Estados
import app.cooperativa.presentation.mainflow.socios.historial.mainHistorial.SHistorialScreen
import app.cooperativa.presentation.mainflow.socios.historial.mainHistorial.SHistorialState
import app.cooperativa.theme.CoopTheme
import app.cooperativa.data.model.dto.Prestamo
import app.cooperativa.data.model.dto.PrestamoDetalle
import kotlinx.datetime.LocalDate

// Datos de prueba
private val dummyPrestamos = listOf(
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

// Estado base para previews
private val dummyState = SHistorialState(
    selectedTabIndex = 0,
    totalAportado = 1200.0f,
    capitalPorPagar = 3000.0f,
    prestamos = dummyPrestamos
)

// === PREVIEWS ===

@Preview(
    name = "Historial Pagos - Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SHistorialPagosPreviewLight() {
    CoopTheme {
        SHistorialScreen(
            state = dummyState.copy(selectedTabIndex = 0),
            loadData = {},
            onTabSelected = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Historial Pagos - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SHistorialPagosPreviewDark() {
    CoopTheme {
        SHistorialScreen(
            state = dummyState.copy(selectedTabIndex = 0),
            loadData = {},
            onTabSelected = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Historial Préstamos - Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SHistorialPrestamosPreviewLight() {
    CoopTheme {
        SHistorialScreen(
            state = dummyState.copy(selectedTabIndex = 1),
            loadData = {},
            onTabSelected = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Historial Préstamos - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SHistorialPrestamosPreviewDark() {
    CoopTheme {
        SHistorialScreen(
            state = dummyState.copy(selectedTabIndex = 1),
            loadData = {},
            onTabSelected = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}