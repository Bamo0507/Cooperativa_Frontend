package app.cooperativa.presentation.mainflow.socios.pagos

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import app.cooperativa.presentation.mainflow.socios.pagos.agregarPago.SPagoEnviarDestination
import app.cooperativa.presentation.mainflow.socios.pagos.agregarPago.spagosEnviarScreen
import app.cooperativa.presentation.mainflow.socios.pagos.pagoStatus.SPagosStatusDestination
import app.cooperativa.presentation.mainflow.socios.pagos.pagoStatus.sociosPagosStatusScreen
import kotlinx.serialization.Serializable

@Serializable
data object SPagosNavGraph

fun NavGraphBuilder.sPagosNavGraph(
    navController: NavController
){
    navigation<SPagosNavGraph>(startDestination = SPagosStatusDestination){
        sociosPagosStatusScreen(
            onAddPaymentClick = {
                navController.navigate(SPagoEnviarDestination)
            }
        )

        spagosEnviarScreen(
            onBackClick = {
                navController.navigateUp()
            }
        )
    }
}