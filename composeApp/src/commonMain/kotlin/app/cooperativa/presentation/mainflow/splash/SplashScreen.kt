package app.cooperativa.presentation.mainflow.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import app.cooperativa.presentation.login.LoginDestination
import app.cooperativa.presentation.mainflow.directiva.DirectivaMainNavigation
import app.cooperativa.presentation.mainflow.socios.SociosMainNavigation
import app.cooperativa.theme.CoopTheme
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
    navController: NavController
){
    LaunchedEffect(state.isLoading){
        if(!state.isLoading && !state.hasLoggedIn){
            navController.navigate(LoginDestination) {
                popUpTo(SplashDestination) { inclusive = true }
            }
        } else if(!state.isLoading && state.hasLoggedIn){
            // Determine flow according to user type
            if(state.userType == "Directive") {
                navController.navigate(DirectivaMainNavigation) {
                    popUpTo(SplashDestination) { inclusive = true }
                }
            } else {
                navController.navigate(SociosMainNavigation) {
                    popUpTo(SplashDestination) { inclusive = true }
                }
            }
        }
    }

    if(state.isLoading){
        SplashScreen()
    }
}

@Composable
fun SplashScreen() {
    val shimmerColors = listOf(
        CoopTheme.colorScheme.primary.copy(alpha = 0.2f),
        CoopTheme.colorScheme.primary,
        CoopTheme.colorScheme.primary.copy(alpha = 0.2f)
    )
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = -300f, targetValue = 300f,
        animationSpec = infiniteRepeatable(
            tween(1200, easing = LinearEasing),
            RepeatMode.Restart
        )
    )
    Box(Modifier.fillMaxSize().background(CoopTheme.colorScheme.surfaceVariant)) {
        // Shimmer brush
        val brush = Brush.horizontalGradient(shimmerColors, startX = translateAnim, endX = translateAnim + 250f)
        CoopText(
            text = "CSPI",
            style = CoopTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            ),
            color = CoopTheme.colorScheme.onSurface,
            modifier = Modifier
                .align(Alignment.Center)
                .drawWithCache {
                    onDrawWithContent {
                        drawContent()
                        drawRect(brush = brush, blendMode = BlendMode.SrcAtop)
                    }
                }
                .padding(horizontal = 16.dp)
        )
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .align(Alignment.BottomCenter),
            color = CoopTheme.colorScheme.primary
        )
    }
}