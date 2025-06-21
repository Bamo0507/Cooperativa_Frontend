package app.cooperativa.presentation.mainflow.socios

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Serializable
data object SociosMainNavigation

fun NavGraphBuilder.sociosMainNavigationGraph(
    onLogOutClick: () -> Unit,
    onChangeToDirectiva: () -> Unit
){
    composable<SociosMainNavigation> {
        val nestedNavController = rememberNavController()
        SociosMainFlowScreen(
            navController = nestedNavController,
            onLogOutClick = onLogOutClick,
            onChangeToDirectiva = onChangeToDirectiva
        )
    }
}
