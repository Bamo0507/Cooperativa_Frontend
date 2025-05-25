package app.cooperativa.presentation.mainflow.directiva.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import app.cooperativa.presentation.mainflow.directiva.DirectivaMainNavigation
import app.cooperativa.presentation.mainflow.directiva.pagos.DPaymentNavGraph
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
        SplashOption3()
    }
}

@Composable
fun SplashOption1() {
    // animación de pulso
    val pulse by rememberInfiniteTransition().animateFloat(
        initialValue = 0.9f, targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            tween(800, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        CoopTheme.colorScheme.primary,
                        CoopTheme.colorScheme.secondary
                    )
                )
            )
    ) {
        Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            CoopText(
                text = "COOPERATIVA",
                style = CoopTheme.typography.headlineMedium.copy(
                    color = CoopTheme.colorScheme.onPrimary,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(Modifier.height(24.dp))
            Icon(
                imageVector = Icons.Default.AccountBalance,
                contentDescription = null,
                tint = CoopTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(64.dp)
                    .graphicsLayer { this.scaleX = pulse; scaleY = pulse }
            )
        }
    }
}

@Composable
fun SplashOption2() {
    val transition = rememberInfiniteTransition()
    val offsets = List(3) { idx ->
        transition.animateFloat(
            initialValue = 300f, targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(600, delayMillis = idx * 200, easing = LinearOutSlowInEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }
    val color = CoopTheme.colorScheme.onPrimary
    Box(Modifier.fillMaxSize().background(CoopTheme.colorScheme.surface)) {
        offsets.forEachIndexed { idx, anim ->
            Canvas(
                Modifier
                    .size(50.dp + (idx * 20).dp)
                    .offset { IntOffset(x = idx * 60, y = anim.value.toInt()) }
                    .alpha(0.3f)
            ) {
                drawCircle(color = color)
            }
        }
        CoopText(
            text = "Cooperativa",
            style = CoopTheme.typography.headlineLarge.copy(fontWeight = FontWeight.ExtraBold),
            color = CoopTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun SplashOption3() {
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