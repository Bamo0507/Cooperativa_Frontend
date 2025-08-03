package app.cooperativa.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import app.cooperativa.presentation.login.LoginDestination
import app.cooperativa.presentation.login.loginScreen
import app.cooperativa.presentation.mainflow.directiva.DirectivaMainNavigation
import app.cooperativa.presentation.mainflow.directiva.directivaMainNavigationGraph
import app.cooperativa.presentation.mainflow.splash.SplashDestination
import app.cooperativa.presentation.mainflow.splash.directivasplashScreen
import app.cooperativa.presentation.mainflow.socios.SociosMainNavigation
import app.cooperativa.presentation.mainflow.socios.sociosMainNavigationGraph

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
            },
            onChangeToSocios = {
                navController.navigate(SociosMainNavigation)
            }
        )

        sociosMainNavigationGraph(
            onLogOutClick = {
                navController.navigate(LoginDestination) {
                    popUpTo(0)
                }
            },
            onChangeToDirectiva = {
                navController.navigate(DirectivaMainNavigation)
            }
        )


    }
}