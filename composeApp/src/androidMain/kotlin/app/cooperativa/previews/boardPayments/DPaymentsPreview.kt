package app.cooperativa.previews.boardPayments

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.cooperativa.data.localdb.PaymentMockData
import app.cooperativa.data.model.ui.BasicInfoPayment
import app.cooperativa.presentation.mainflow.directiva.pagos.DPaymentsScreen
import app.cooperativa.theme.CoopTheme

private val samplePendientes = PaymentMockData.getAllPaymentsBasicInfo()

@Preview(
    name = "Pendientes Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun DPaymentsPendingPreviewLight() {
    CoopTheme {
        DPaymentsScreen(
            payments = samplePendientes,
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
            payments = samplePendientes,
            modifier = Modifier.padding(16.dp)
        )
    }
}
