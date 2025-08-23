package app.cooperativa.presentation.mainflow.socios.pagos.pagoError

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class SPagoErrorDestination(
    val paymentId: String
)

fun NavController.navigateToSPagoError(
    destination: SPagoErrorDestination,
    navOptions: NavOptions? = null
){
    this.navigate(
        destination,
        navOptions
    )
}

fun NavGraphBuilder.spagoErrorScreen(
    onBackClick: () -> Unit
){
    composable<SPagoErrorDestination> { backStackEntry ->
        val destination: SPagoErrorDestination = backStackEntry.toRoute()
        SPagoErrorRoute(
            paymentId = destination.paymentId,
            onBackClick = onBackClick
        )
    }
}