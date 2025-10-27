package app.cooperativa.presentation.mainflow.socios.pagos.pagoStatus

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.cooperativa.navigation.utils.NavResultKeys
import app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral.DPaymentsRoute
import app.cooperativa.presentation.utils.ConfettiOverlay
import kotlinx.serialization.Serializable

@Serializable
data object SPagosStatusDestination

fun NavGraphBuilder.sociosPagosStatusScreen(
    onAddPaymentClick: () -> Unit,
    onWatchError: (String) -> Unit
){
    composable<SPagosStatusDestination> { backStackEntry ->
        var showConfetti by rememberSaveable { mutableStateOf(false) }

        val confettiFlag by backStackEntry
            .savedStateHandle
            .getStateFlow(NavResultKeys.CONFETTI, false)
            .collectAsStateWithLifecycle()

        LaunchedEffect(confettiFlag) {
            if (confettiFlag) {
                showConfetti = true
                backStackEntry.savedStateHandle[NavResultKeys.CONFETTI] = false
            }
        }

        Box(Modifier.fillMaxSize()) {
            SPagosStatusRoute(
                onAddPaymentClick,
                onWatchError
            )

            ConfettiOverlay(
                visible = showConfetti,
                onFinished = { showConfetti = false },
                modifier = Modifier.matchParentSize()
            )
        }
    }
}