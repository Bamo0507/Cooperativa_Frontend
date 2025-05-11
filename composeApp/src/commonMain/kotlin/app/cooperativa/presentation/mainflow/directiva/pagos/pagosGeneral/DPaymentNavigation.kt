package app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object GeneralPaymentDestination

fun NavGraphBuilder.boardGeneralPayment(
    onPaymentClick: (Int) -> Unit
) {
    composable<GeneralPaymentDestination> {
        DPaymentsRoute(
            onPaymentClick = onPaymentClick
        )
    }
}