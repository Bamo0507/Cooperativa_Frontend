package app.cooperativa.presentation.mainflow.socios.prestamos.solicitudPrestamo

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SSolicitudPrestamoDestination

fun NavGraphBuilder.solicitudPrestamoScreen(
    onBackClick: () -> Unit
){
    composable<SSolicitudPrestamoDestination>{
        SolicitudPrestamoRoute(
            onBackClick = onBackClick
        )
    }
}