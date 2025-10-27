package app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral

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
import app.cooperativa.presentation.utils.ConfettiOverlay
import kotlinx.serialization.Serializable

@Serializable
data object GeneralPaymentDestination

fun NavGraphBuilder.boardGeneralPayment(
    onPendingPaymentClick: (String) -> Unit,
    onPaidPaymentClick: (String) -> Unit,
    onFineClick: (String) -> Unit
) {
    composable<GeneralPaymentDestination> { backStackEntry ->
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
            DPaymentsRoute(
                onPendingPaymentClick = onPendingPaymentClick,
                onPaidPaymentClick = onPaidPaymentClick,
                onFineClick = onFineClick
            )

            ConfettiOverlay(
                visible = showConfetti,
                onFinished = { showConfetti = false },
                modifier = Modifier.matchParentSize()
            )
        }

    }
}