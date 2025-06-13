package app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object GeneralPaymentDestination

fun NavGraphBuilder.boardGeneralPayment(
    onPendingPaymentClick: (Int) -> Unit,
    onPaidPaymentClick: (Int) -> Unit,
    onFineClick: (Int) -> Unit
) {
    composable<GeneralPaymentDestination> {
        DPaymentsRoute(
            onPendingPaymentClick = onPendingPaymentClick,
            onPaidPaymentClick = onPaidPaymentClick,
            onFineClick = onFineClick
        )
    }
}