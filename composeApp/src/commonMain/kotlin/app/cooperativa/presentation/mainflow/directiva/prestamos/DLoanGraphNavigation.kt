package app.cooperativa.presentation.mainflow.directiva.prestamos

import kotlinx.serialization.Serializable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import app.cooperativa.presentation.mainflow.directiva.prestamos.loanRequestDetail.SolicitudPrestamoDestination
import app.cooperativa.presentation.mainflow.directiva.prestamos.loanRequestDetail.navigateToSolicitudPrestamoScreen
import app.cooperativa.presentation.mainflow.directiva.prestamos.loanRequestDetail.solicitudPrestamoScreen
import app.cooperativa.presentation.mainflow.directiva.prestamos.prestamosGeneral.PrestamoNavigationDestination
import app.cooperativa.presentation.mainflow.directiva.prestamos.prestamosGeneral.boardPrestamos

@Serializable
data object DLoanNavGraph

fun NavGraphBuilder.dLoanNavGraph(
    navController: NavController
){
    navigation<DLoanNavGraph>(startDestination = PrestamoNavigationDestination){
        boardPrestamos(
            onPrestamoClick = { prestamoId ->
                navController.navigateToSolicitudPrestamoScreen(
                    destination = SolicitudPrestamoDestination(
                        solicitudId = prestamoId
                    )
                )
            }
        )
        solicitudPrestamoScreen (
            onBackClick = {
                navController.navigateUp()
            }
        )
    }
}