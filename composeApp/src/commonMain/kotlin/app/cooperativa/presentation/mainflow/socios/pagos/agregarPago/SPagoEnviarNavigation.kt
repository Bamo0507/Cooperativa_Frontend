package app.cooperativa.presentation.mainflow.socios.pagos.agregarPago

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SPagoEnviarDestination

fun NavGraphBuilder.spagosEnviarScreen(
    onBackClick: () -> Unit
){
    composable<SPagoEnviarDestination> {
        SPagoEnviarRoute(
            onBackClick = onBackClick
        )
    }
}