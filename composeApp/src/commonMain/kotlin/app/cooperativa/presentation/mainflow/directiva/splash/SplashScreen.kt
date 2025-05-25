package app.cooperativa.presentation.mainflow.directiva.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import app.cooperativa.presentation.mainflow.directiva.DirectivaMainNavigation
import app.cooperativa.presentation.mainflow.directiva.pagos.DPaymentNavGraph
import app.cooperativa.theme.components.CoopText
import org.koin.compose.koinInject

@Composable
fun SplashRoute(
    navController: NavController,
    viewModel: SplashViewModel = koinInject()
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    SplashScreen(
        state = state,
        navController = navController
    )
}

@Composable
fun SplashScreen(
    state: SplashState,
    navController: NavController,
    modifier: Modifier = Modifier
){
    LaunchedEffect(state.isLoading){
        if(!state.isLoading){
            navController.navigate(DirectivaMainNavigation) {
                popUpTo(SplashDestination) { inclusive = true }
            }
        }
    }

    if(state.isLoading){
        CoopText(
            text = "Splash Screen",
            modifier = modifier
        )
    }
}