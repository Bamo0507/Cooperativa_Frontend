package app.cooperativa.presentation.mainflow.socios.historial.mainHistorial

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data object SHistorialDestination

fun NavGraphBuilder.sociosHistorialScreen(){
    composable<SHistorialDestination> {
        SHistorialRoute()
    }
}