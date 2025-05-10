package app.cooperativa.previews.boardPayments

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.cooperativa.data.localdb.FineMockData
import app.cooperativa.data.localdb.PaymentMockData
import app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral.DPaymentsScreen
import app.cooperativa.theme.CoopTheme

private val samplePendientes = PaymentMockData.getAllPaymentsBasicInfo()
private val sampleFines = FineMockData.getAllFines()

@Preview(
    name = "Pendientes Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun DPaymentsPendingPreviewLight() {
    CoopTheme {
        DPaymentsScreen(
            fines = sampleFines,
            payments = samplePendientes,
            selectedTabIndex = 0,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Pendientes Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DPaymentsPendingPreviewDark() {
    CoopTheme {
        DPaymentsScreen(
            fines = sampleFines,
            payments = samplePendientes,
            selectedTabIndex = 0,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Pagados Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun DPaymentsPayedPreviewLight() {
    CoopTheme {
        DPaymentsScreen(
            fines = sampleFines,
            payments = samplePendientes,
            selectedTabIndex = 1,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Pagados Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DPaymentsPayedPreviewDark() {
    CoopTheme {
        DPaymentsScreen(
            fines = sampleFines,
            payments = samplePendientes,
            selectedTabIndex = 1,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Moras Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun DPaymentsFinesPreviewLight() {
    CoopTheme {
        DPaymentsScreen(
            fines = sampleFines,
            payments = samplePendientes,
            selectedTabIndex = 2,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Moras Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DPaymentsFinesPreviewDark() {
    CoopTheme {
        DPaymentsScreen(
            fines = sampleFines,
            payments = samplePendientes,
            selectedTabIndex = 2,
            modifier = Modifier.padding(16.dp)
        )
    }
}