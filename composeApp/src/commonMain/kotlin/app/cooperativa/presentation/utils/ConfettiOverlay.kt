package app.cooperativa.presentation.utils

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import io.github.vinceglb.confettikit.compose.ConfettiKit
import io.github.vinceglb.confettikit.core.Party
import io.github.vinceglb.confettikit.core.Position
import io.github.vinceglb.confettikit.core.emitter.Emitter
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun ConfettiOverlay(
    visible: Boolean,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (!visible) return

    val colors = remember { listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def, 0xb2ffff) }

    val leftParty = remember {
        Party(
            speed = 5f,
            maxSpeed = 30f,
            angle = -45,
            spread = 60,
            damping = 0.9f,
            colors = colors,
            emitter = Emitter(duration = 600.milliseconds).max(140),
            position = Position.Relative(0.0, 0.20),
        )
    }
    val rightParty = remember {
        Party(
            speed = 5f,
            maxSpeed = 30f,
            angle = -135,
            spread = 60,
            damping = 0.9f,
            colors = colors,
            emitter = Emitter(duration = 600.milliseconds).max(140),
            position = Position.Relative(1.0, 0.20),
        )
    }

    // Oculta el overlay luego de que termina la emisión
    LaunchedEffect(Unit) {
        delay(3200) // > 600ms de la emisión para que termine cómodo
        onFinished()
    }

    ConfettiKit(
        modifier = modifier
            .fillMaxSize()
            .zIndex(1f),
        parties = listOf(leftParty, rightParty),
    )
}