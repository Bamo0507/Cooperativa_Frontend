package app.cooperativa.presentation.mainflow.socios.pagos.pagoStatus

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SPagosStatusDestination

fun NavGraphBuilder.sociosPagosStatusScreen(){
    composable<SPagosStatusDestination> {
        SPagosStatusRoute()
    }
}