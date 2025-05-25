package app.cooperativa.presentation.mainflow.directiva.pagos.paymentDetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class DPaidPayDestination(
    val paymentId: Int
)

fun NavController.navigateToDPaidPayScreen(
    destination: DPaidPayDestination,
    navOptions: NavOptions? = null
){
    this.navigate(
        destination,
        navOptions
    )
}

fun NavGraphBuilder.paidPaymentScreen(onBackClick: () -> Unit){
    composable<DPaidPayDestination> { backStackEntry ->
        val destination: DPaidPayDestination = backStackEntry.toRoute()
        DPaidPayRoute(
            paymentId = destination.paymentId,
            onBackClick = onBackClick
        )
    }
}