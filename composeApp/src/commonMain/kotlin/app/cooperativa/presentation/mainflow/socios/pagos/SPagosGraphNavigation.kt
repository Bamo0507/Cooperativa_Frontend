package app.cooperativa.presentation.mainflow.socios.pagos

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import app.cooperativa.presentation.mainflow.socios.pagos.pagoStatus.SPagosStatusDestination
import app.cooperativa.presentation.mainflow.socios.pagos.pagoStatus.sociosPagosStatusScreen
import kotlinx.serialization.Serializable

@Serializable
data object SPagosNavGraph

fun NavGraphBuilder.sPagosNavGraph(){
    navigation<SPagosNavGraph>(startDestination = SPagosStatusDestination){
        sociosPagosStatusScreen()
    }
}