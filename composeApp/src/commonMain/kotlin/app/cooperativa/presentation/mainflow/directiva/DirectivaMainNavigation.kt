package app.cooperativa.presentation.mainflow.directiva

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Serializable
data object DirectivaMainNavigation

fun NavGraphBuilder.directivaMainNavigationGraph(
    onLogOutClick: ()->Unit,
){
    composable<DirectivaMainNavigation> {
        val nestedNavController = rememberNavController()
        DirectivaMainFlowScreen(
            navController = nestedNavController,
            onLogOutClick = onLogOutClick
        )
    }
}