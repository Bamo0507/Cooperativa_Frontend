package app.cooperativa.previews.boardLoans

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.cooperativa.data.localdb.SolicitudPrestamoMockData
import app.cooperativa.presentation.mainflow.directiva.prestamos.loanRequestDetail.SolicitudPrestamoScreen
import app.cooperativa.theme.CoopTheme

// Datos de ejemplo para dos solicitudes
private val sampleSolicitud1 = SolicitudPrestamoMockData.getSolicitudById(1)!!
private val sampleSolicitud2 = SolicitudPrestamoMockData.getSolicitudById(2)!!

@Preview(
    name = "Solicitud Detalle Light 1",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SolicitudPrestamoDetailPreviewLight1() {
    CoopTheme {
        SolicitudPrestamoScreen(
            prestamo = sampleSolicitud1,
            interestInput = 0f,
            commentsInput = "",
            onInterestChange = {},
            onCommentsChange = {},
            onApprove = {},
            onReject = {},
            onBackClick = {},
            modifier = Modifier
        )
    }
}

@Preview(
    name = "Solicitud Detalle Dark 1",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SolicitudPrestamoDetailPreviewDark1() {
    CoopTheme {
        SolicitudPrestamoScreen(
            prestamo = sampleSolicitud1,
            interestInput = 0f,
            commentsInput = "",
            onInterestChange = {},
            onCommentsChange = {},
            onApprove = {},
            onReject = {},
            onBackClick = {},
            modifier = Modifier
        )
    }
}

@Preview(
    name = "Solicitud Detalle Light 2",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SolicitudPrestamoDetailPreviewLight2() {
    CoopTheme {
        SolicitudPrestamoScreen(
            prestamo = sampleSolicitud2,
            interestInput = 0f,
            commentsInput = "",
            onInterestChange = {},
            onCommentsChange = {},
            onApprove = {},
            onReject = {},
            onBackClick = {},
            modifier = Modifier
        )
    }
}

@Preview(
    name = "Solicitud Detalle Dark 2",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SolicitudPrestamoDetailPreviewDark2() {
    CoopTheme {
        SolicitudPrestamoScreen(
            prestamo = sampleSolicitud2,
            interestInput = 0f,
            commentsInput = "",
            onInterestChange = {},
            onCommentsChange = {},
            onApprove = {},
            onReject = {},
            onBackClick = {},
            modifier = Modifier
        )
    }
}
