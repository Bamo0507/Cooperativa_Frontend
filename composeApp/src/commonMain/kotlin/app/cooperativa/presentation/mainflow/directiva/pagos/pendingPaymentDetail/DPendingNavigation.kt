package app.cooperativa.presentation.mainflow.directiva.pagos.pendingPaymentDetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class PendingPaymentDestination(
    val paymentId: Int
)

fun NavController.navigateToPendingPaymentScreen(
    destination: PendingPaymentDestination,
    navOptions: NavOptions? = null
){
    this.navigate(
        destination,
        navOptions
    )
}

fun NavGraphBuilder.pendingPaymentScreen(
    onBackClick: () -> Unit
){
    composable<PendingPaymentDestination>{ backStackEntry ->
        val destination: PendingPaymentDestination = backStackEntry.toRoute()

        DPendingPayRoute(
            paymentId = destination.paymentId,
            onBackClick = onBackClick
        )
    }
}