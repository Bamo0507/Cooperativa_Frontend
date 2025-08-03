package app.cooperativa.presentation.mainflow.directiva.manager.fine

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object DFineManagerDestination

fun NavGraphBuilder.fineForm(
    onBackClick: () -> Unit
){
    composable<DFineManagerDestination> {
        DFineManagerRoute(
            onBackClick = onBackClick
        )
    }
}