package app.cooperativa.presentation.mainflow.directiva.prestamos.loanRequestDetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class SolicitudPrestamoDestination(
    val solicitudId: Int
)

fun NavController.navigateToSolicitudPrestamoScreen(
    destination: SolicitudPrestamoDestination,
    navOptions: NavOptions? = null
){
    this.navigate(
        destination,
        navOptions
    )
}

fun NavGraphBuilder.solicitudPrestamoScreen(
    onBackClick: () -> Unit
){
    composable<SolicitudPrestamoDestination> { backStackEntry ->
        val destination: SolicitudPrestamoDestination = backStackEntry.toRoute()
        SolicitudPrestamoRoute(
            solicitudId = destination.solicitudId,
            onBackClick = onBackClick
        )
    }
}
