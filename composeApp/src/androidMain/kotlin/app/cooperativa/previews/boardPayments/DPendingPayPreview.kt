package app.cooperativa.previews.boardPayments

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import app.cooperativa.data.localdb.PaymentMockData
import app.cooperativa.presentation.mainflow.directiva.pagos.pendingPaymentDetail.DPendingPayScreen
import app.cooperativa.theme.CoopTheme

@Preview(
    name = "DPendingPayScreen Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4
)
@Composable
fun DPendingPayScreenLightPreview() {
    CoopTheme(darkTheme = false) {
        PaymentMockData.getPaymentById(1)?.let {
            DPendingPayScreen(
                payment = it,
                onBackClick = {}
            )
        }
    }
}

@Preview(
    name = "DPendingPayScreen Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_4
)
@Composable
fun DPendingPayScreenDarkPreview() {
    CoopTheme(darkTheme = true) {
        PaymentMockData.getPaymentById(1)?.let {
            DPendingPayScreen(
                payment = it,
                onBackClick = {}
            )
        }
    }
}

@Preview(
    name = "DPendingPayScreen FullLight",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4
)
@Composable
fun DPendingPayScreenLightFullPreview() {
    CoopTheme(darkTheme = false) {
        PaymentMockData.getPaymentById(8)?.let {
            DPendingPayScreen(
                payment = it,
                onBackClick = {}
            )
        }
    }
}

@Preview(
    name = "DPendingPayScreen FullDark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_4
)
@Composable
fun DPendingPayScreenDarkFullPreview() {
    CoopTheme(darkTheme = true) {
        PaymentMockData.getPaymentById(8)?.let {
            DPendingPayScreen(
                payment = it,
                onBackClick = {}
            )
        }
    }
}
