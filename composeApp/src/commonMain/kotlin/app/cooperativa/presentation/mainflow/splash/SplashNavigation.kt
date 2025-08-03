package app.cooperativa.presentation.mainflow.splash

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SplashDestination

fun NavGraphBuilder.directivasplashScreen(
    navController: NavController
) {
    composable<SplashDestination> {
        SplashRoute(
            navController = navController
        )
    }
}