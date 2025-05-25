package app.cooperativa.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import app.cooperativa.presentation.login.LoginDestination
import app.cooperativa.presentation.login.loginScreen
import app.cooperativa.presentation.mainflow.directiva.DirectivaMainNavigation
import app.cooperativa.presentation.mainflow.directiva.directivaMainNavigationGraph
import app.cooperativa.presentation.mainflow.directiva.pagos.dPaymentNavGraph
import app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral.GeneralPaymentDestination
import app.cooperativa.presentation.mainflow.directiva.splash.SplashDestination
import app.cooperativa.presentation.mainflow.directiva.splash.directivasplashScreen

@Composable
fun AppNavigation(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = SplashDestination
    ){
        directivasplashScreen(
            navController = navController
        )

        //TODO: MANEJAR HACIA QUE MAINFLOW LO MANDO SI ES SOCIO O DIRECTIVA
        loginScreen(
            onLogin = {
                navController.navigate(DirectivaMainNavigation){
                    popUpTo(LoginDestination){
                        inclusive = true
                    }
                }
            }
        )

        directivaMainNavigationGraph(
            onLogOutClick = {
                navController.navigate(LoginDestination){
                    popUpTo(0)
                }
            }
        )


    }
}