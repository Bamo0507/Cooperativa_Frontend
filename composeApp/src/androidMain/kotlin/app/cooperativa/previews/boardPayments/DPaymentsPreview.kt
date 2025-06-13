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
import app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral.DPaymentsState
import app.cooperativa.theme.CoopTheme

// Datos de ejemplo
private val samplePayments = PaymentMockData.getAllPaymentsBasicInfo()
private val sampleFines = FineMockData.getAllFines()

private val pendingPayments = samplePayments.filter { it.isPaymentPending }
private val paidPayments = samplePayments.filter { !it.isPaymentPending }

@Preview(
    name = "Pendientes Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun DPaymentsPendingPreviewLight() {
    CoopTheme {
        DPaymentsScreen(
            state = DPaymentsState(
                selectedTabIndex = 0,
                pendingPayments = pendingPayments,
                paidPayments = paidPayments,
                fines = sampleFines
            ),
            onTabSelected = {},
            onSearchQueryChange = {},
            onPendingPaymentClick = {},
            onPaidPaymentClick = {},
            modifier = Modifier.padding(16.dp),
            onFineClick = {},
            loadData = {}
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
            state = DPaymentsState(
                selectedTabIndex = 0,
                pendingPayments = pendingPayments,
                paidPayments = paidPayments,
                fines = sampleFines
            ),
            onTabSelected = {},
            onSearchQueryChange = {},
            onPendingPaymentClick = {},
            onPaidPaymentClick = {},
            modifier = Modifier.padding(16.dp),
            onFineClick = {},
            loadData = {}
        )
    }
}

@Preview(
    name = "Pagados Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun DPaymentsPaidPreviewLight() {
    CoopTheme {
        DPaymentsScreen(
            state = DPaymentsState(
                selectedTabIndex = 1,
                pendingPayments = pendingPayments,
                paidPayments = paidPayments,
                fines = sampleFines
            ),
            onTabSelected = {},
            onSearchQueryChange = {},
            onPendingPaymentClick = {},
            onPaidPaymentClick = {},
            modifier = Modifier.padding(16.dp),
            onFineClick = {},
            loadData = {}
        )
    }
}

@Preview(
    name = "Pagados Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DPaymentsPaidPreviewDark() {
    CoopTheme {
        DPaymentsScreen(
            state = DPaymentsState(
                selectedTabIndex = 1,
                pendingPayments = pendingPayments,
                paidPayments = paidPayments,
                fines = sampleFines
            ),
            onTabSelected = {},
            onSearchQueryChange = {},
            onPendingPaymentClick = {},
            onPaidPaymentClick = {},
            modifier = Modifier.padding(16.dp),
            onFineClick = {},
            loadData = {}
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
            state = DPaymentsState(
                selectedTabIndex = 2,
                pendingPayments = pendingPayments,
                paidPayments = paidPayments,
                fines = sampleFines
            ),
            onTabSelected = {},
            onSearchQueryChange = {},
            onPendingPaymentClick = {},
            onPaidPaymentClick = {},
            modifier = Modifier.padding(16.dp),
            onFineClick = {},
            loadData = {}
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
            state = DPaymentsState(
                selectedTabIndex = 2,
                pendingPayments = pendingPayments,
                paidPayments = paidPayments,
                fines = sampleFines
            ),
            onTabSelected = {},
            onSearchQueryChange = {},
            onPendingPaymentClick = {},
            onPaidPaymentClick = {},
            modifier = Modifier.padding(16.dp),
            loadData = {},
            onFineClick = {}
        )
    }
}
