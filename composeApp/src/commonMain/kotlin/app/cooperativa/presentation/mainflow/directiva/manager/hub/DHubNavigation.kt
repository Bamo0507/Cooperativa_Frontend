package app.cooperativa.presentation.mainflow.directiva.manager.hub

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object DHubDestination

fun NavGraphBuilder.hubManager(
    onFineClick: () -> Unit,
    onLoanClick: () -> Unit
) {
    composable<DHubDestination> {
        DHubRoute(
            onFineClick = onFineClick,
            onLoanClick = onLoanClick
        )
    }
}