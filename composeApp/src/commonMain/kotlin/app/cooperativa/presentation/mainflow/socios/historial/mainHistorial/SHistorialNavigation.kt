package app.cooperativa.presentation.mainflow.socios.historial.mainHistorial

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class SHistorialDestination(
    val userId: Int
)

fun NavController.navigateToSHistorialScreen(
    destination: SHistorialDestination,
    navOptions: NavOptions? = null
){
    this.navigate(
        destination,
        navOptions
    )
}


fun NavGraphBuilder.sociosHistorialScreen(){
    composable<SHistorialDestination> { backStackEntry ->
        val destination: SHistorialDestination = backStackEntry.toRoute()
        SHistorialRoute(
            userId = destination.userId
        )
    }
}