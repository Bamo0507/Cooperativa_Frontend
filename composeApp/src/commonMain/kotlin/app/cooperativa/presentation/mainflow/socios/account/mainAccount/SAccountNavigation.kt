package app.cooperativa.presentation.mainflow.socios.account.mainAccount

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SAccountDestination

fun NavGraphBuilder.sAccountScreen(
    onLogOutClick: () -> Unit,
    onChangeToDirectiva: () -> Unit
){
    composable<SAccountDestination>{
        SAccountRoute(
            onLogOutClick = onLogOutClick,
            onChangeToDirectiva = onChangeToDirectiva
        )
    }
}