package app.cooperativa.presentation.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination

//Función para dejar el cuento como composable
fun NavGraphBuilder.loginScreen(
    onLogin: (user_type: String) -> Unit
){
    composable<LoginDestination>{
        LoginRoute(
            onLogin = onLogin
        )
    }
}