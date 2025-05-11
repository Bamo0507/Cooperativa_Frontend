package app.cooperativa.previews.boardLoans

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.cooperativa.data.localdb.SolicitudPrestamoMockData
import app.cooperativa.data.localdb.PrestamoMockData
import app.cooperativa.presentation.mainflow.directiva.prestamos.prestamosGeneral.PrestamoScreen
import app.cooperativa.theme.CoopTheme

private val sampleReqLoans = SolicitudPrestamoMockData.getAllBasicInfo()
private val sampleApprovedLoans = PrestamoMockData.getAllPrestamos()

@Preview(
    name = "Solicitudes Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun PrestamosSolicitudesPreviewLight() {
    CoopTheme {
        PrestamoScreen(
            reqLoans = sampleReqLoans,
            approvedLoans = sampleApprovedLoans,
            selectedTabIndex = 0,
            changeIndex = {},
            onPendingLoanClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Solicitudes Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PrestamosSolicitudesPreviewDark() {
    CoopTheme {
        PrestamoScreen(
            reqLoans = sampleReqLoans,
            approvedLoans = sampleApprovedLoans,
            selectedTabIndex = 0,
            changeIndex = {},
            onPendingLoanClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Vigentes Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun PrestamosVigentesPreviewLight() {
    CoopTheme {
        PrestamoScreen(
            reqLoans = sampleReqLoans,
            approvedLoans = sampleApprovedLoans,
            selectedTabIndex = 1,
            changeIndex = {},
            onPendingLoanClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Vigentes Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PrestamosVigentesPreviewDark() {
    CoopTheme {
        PrestamoScreen(
            reqLoans = sampleReqLoans,
            approvedLoans = sampleApprovedLoans,
            selectedTabIndex = 1,
            changeIndex = {},
            onPendingLoanClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}


@Preview(
    name = "Completados Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun PrestamosCompletadosPreviewLight() {
    CoopTheme {
        PrestamoScreen(
            reqLoans = sampleReqLoans,
            approvedLoans = sampleApprovedLoans,
            selectedTabIndex = 2,
            changeIndex = {},
            onPendingLoanClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Completados Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PrestamosCompletadosPreviewDark() {
    CoopTheme {
        PrestamoScreen(
            reqLoans = sampleReqLoans,
            approvedLoans = sampleApprovedLoans,
            selectedTabIndex = 2,
            changeIndex = {},
            onPendingLoanClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

