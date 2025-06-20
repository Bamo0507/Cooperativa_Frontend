package app.cooperativa.presentation.mainflow.socios.prestamos.mainPrestamos

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SPrestamoDestination

fun NavGraphBuilder.sociosPrestamosScreen(){
    composable<SPrestamoDestination> {
        SPrestamoRoute()
    }
}