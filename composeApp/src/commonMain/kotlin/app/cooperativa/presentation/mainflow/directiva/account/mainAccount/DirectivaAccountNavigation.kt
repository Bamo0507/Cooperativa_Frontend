package app.cooperativa.presentation.mainflow.directiva.account.mainAccount

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object DirectivaAccountDestination

fun NavGraphBuilder.directivaAccountScreen(
    onLogOutClick: ()->Unit,
    onChangeToMember: ()->Unit = {}, //TODO: IMPLEMENTAR
){
    composable<DirectivaAccountDestination> {
        DirectivaAccountRoute(
            onChangeToMember = onChangeToMember,
            onLogout = onLogOutClick
        )
    }
}