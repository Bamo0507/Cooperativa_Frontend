package app.cooperativa.presentation.mainflow.directiva.prestamos.pagaresDetail

import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class DPagaresDestination(val pagareId: Int)

fun NavController.navigateToDPagareScreen(
    destination: DPagaresDestination,
    navOptions: NavOptions? = null
){
    this.navigate(
        destination,
        navOptions
    )
}

fun NavGraphBuilder.pagareDetailScreen(
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit
){
    composable<DPagaresDestination> { backStackEntry ->
        val destination: DPagaresDestination = backStackEntry.toRoute()
        DPagaresRoute(
            pagareId = destination.pagareId,
            onBackClick = onBackClick,
            onConfirmClick = onConfirmClick
        )
    }
}