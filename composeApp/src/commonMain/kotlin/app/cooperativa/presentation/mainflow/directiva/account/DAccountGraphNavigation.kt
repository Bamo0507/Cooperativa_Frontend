package app.cooperativa.presentation.mainflow.directiva.account

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import app.cooperativa.presentation.mainflow.directiva.account.mainAccount.DirectivaAccountDestination
import app.cooperativa.presentation.mainflow.directiva.account.mainAccount.directivaAccountScreen
import app.cooperativa.presentation.mainflow.socios.historial.mainHistorial.SHistorialDestination
import app.cooperativa.presentation.mainflow.socios.historial.mainHistorial.navigateToSHistorialScreen
import app.cooperativa.presentation.mainflow.socios.historial.mainHistorial.sociosHistorialScreen
import kotlinx.serialization.Serializable

@Serializable
data object DAccountNavGraph

fun NavGraphBuilder.dAccountNavGraph(
    navController: NavController,
    onLogOutClick: ()->Unit,
){
    navigation<DAccountNavGraph>(startDestination = DirectivaAccountDestination){
        directivaAccountScreen(
            onLogOutClick = onLogOutClick,
            onChangeToMember = {
                navController.navigateToSHistorialScreen(
                    destination = SHistorialDestination(
                        userId = 1 //TODO: retrieve ID from datastore
                    )
                )
            }
        )

        sociosHistorialScreen()
    }
}