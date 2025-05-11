package app.cooperativa

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import app.cooperativa.navigation.AppNavigation
import app.cooperativa.presentation.login.LoginRoute
import app.cooperativa.theme.CoopTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import cooperativa.composeapp.generated.resources.Res
import cooperativa.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    CoopTheme {
        AppNavigation(
            navController = navController
        )
    }
}