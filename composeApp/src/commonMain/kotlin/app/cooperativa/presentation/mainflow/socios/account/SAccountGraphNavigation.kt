package app.cooperativa.presentation.mainflow.socios.account

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import app.cooperativa.presentation.mainflow.socios.account.mainAccount.SAccountDestination
import app.cooperativa.presentation.mainflow.socios.account.mainAccount.sAccountScreen
import kotlinx.serialization.Serializable

@Serializable
data object SAccountNavGraph

fun NavGraphBuilder.sAccountNavGraph(
    onLogOutClick: () -> Unit,
    onChangeToDirectiva: () -> Unit
){
    navigation<SAccountNavGraph>(startDestination = SAccountDestination){
        sAccountScreen(
            onLogOutClick = onLogOutClick,
            onChangeToDirectiva = onChangeToDirectiva
        )
    }
}