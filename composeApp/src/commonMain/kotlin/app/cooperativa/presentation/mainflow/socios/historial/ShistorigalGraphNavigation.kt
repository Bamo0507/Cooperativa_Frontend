package app.cooperativa.presentation.mainflow.socios.historial

import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import app.cooperativa.presentation.mainflow.socios.historial.mainHistorial.SHistorialDestination
import app.cooperativa.presentation.mainflow.socios.historial.mainHistorial.sociosHistorialScreen
import kotlinx.serialization.Serializable

@Serializable
data object SHistorialNavGraph

fun NavGraphBuilder.sHistorialNavGraph(){
    navigation<SHistorialNavGraph>(startDestination = SHistorialDestination){
        sociosHistorialScreen()

    }
}