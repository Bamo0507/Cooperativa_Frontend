package app.cooperativa.previews.boardLoans

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.cooperativa.data.localdb.SolicitudPrestamoMockData
import app.cooperativa.presentation.mainflow.directiva.prestamos.loanRequestDetail.SolicitudPrestamoScreen
import app.cooperativa.theme.CoopTheme

private val sampleSolicitud = SolicitudPrestamoMockData.getSolicitudById(1)!!

@Preview(
    name = "Solicitud Detalle Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SolicitudPrestamoDetailPreviewLight() {
    CoopTheme {
        SolicitudPrestamoScreen(
            prestamo = sampleSolicitud,
            onBackClick = {}
        )
    }
}

@Preview(
    name = "Solicitud Detalle Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SolicitudPrestamoDetailPreviewDark() {
    CoopTheme {
        SolicitudPrestamoScreen(
            prestamo = sampleSolicitud,
            onBackClick = {}
        )
    }
}

private val sampleSolicitud2 = SolicitudPrestamoMockData.getSolicitudById(2)!!

@Preview(
    name = "Solicitud Detalle Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SolicitudPrestamoDetailPreviewLight2() {
    CoopTheme {
        SolicitudPrestamoScreen(
            prestamo = sampleSolicitud2,
            onBackClick = {}
        )
    }
}

@Preview(
    name = "Solicitud Detalle Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SolicitudPrestamoDetailPreviewDark2() {
    CoopTheme {
        SolicitudPrestamoScreen(
            prestamo = sampleSolicitud2,
            onBackClick = {}
        )
    }
}
