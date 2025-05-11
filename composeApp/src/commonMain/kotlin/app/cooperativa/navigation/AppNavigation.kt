package app.cooperativa.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import app.cooperativa.presentation.login.LoginDestination
import app.cooperativa.presentation.login.loginScreen
import app.cooperativa.presentation.mainflow.directiva.pagos.dPaymentNavGraph
import app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral.GeneralPaymentDestination

@Composable
fun AppNavigation(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = LoginDestination
    ){
        loginScreen(
            onLogin = {
                navController.navigate(GeneralPaymentDestination){
                    popUpTo(LoginDestination){
                        inclusive = true
                    }
                }
            }
        )

        dPaymentNavGraph(navController)
    }
}