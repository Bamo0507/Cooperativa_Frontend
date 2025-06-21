package app.cooperativa.presentation.mainflow.socios.prestamos

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import app.cooperativa.presentation.mainflow.socios.prestamos.loadPagare.LoadPagareDestination
import app.cooperativa.presentation.mainflow.socios.prestamos.loadPagare.loadPagareScreen
import app.cooperativa.presentation.mainflow.socios.prestamos.mainPrestamos.SPrestamoDestination
import app.cooperativa.presentation.mainflow.socios.prestamos.mainPrestamos.sociosPrestamosScreen
import app.cooperativa.presentation.mainflow.socios.prestamos.solicitudPrestamo.SSolicitudPrestamoDestination
import app.cooperativa.presentation.mainflow.socios.prestamos.solicitudPrestamo.solicitudPrestamoScreen
import kotlinx.serialization.Serializable

@Serializable
data object SPrestamoNavGraph

fun NavGraphBuilder.sPrestamosNavGraph(
    navController: NavController
){
    navigation<SPrestamoNavGraph>(startDestination = SPrestamoDestination){
        sociosPrestamosScreen(
            onLoadPagareClick = {
                navController.navigate(LoadPagareDestination)
            },
            onSolicitudClick = {
                navController.navigate(SSolicitudPrestamoDestination)
            }
        )

        loadPagareScreen(
            onBackClick = {
                navController.navigateUp()
            }
        )

        solicitudPrestamoScreen(
            onBackClick = {
                navController.navigateUp()
            }
        )
    }
}