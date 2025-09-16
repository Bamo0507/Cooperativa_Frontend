package app.cooperativa.presentation.mainflow.socios.pagos.pagoError

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material.icons.filled.DoneAll
import app.cooperativa.presentation.utils.ErrorScreen
import app.cooperativa.presentation.utils.LoadingScreen
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopButton
import app.cooperativa.theme.components.CoopOutlinedCard
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar
import cooperativa.composeapp.generated.resources.Res
import cooperativa.composeapp.generated.resources.ic_payment_error
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun SPagoErrorRoute(
    onBackClick: () -> Unit,
    paymentId: String,
    viewModel: SPagoErrorViewModel = koinInject { parametersOf(paymentId) }
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    SPagoErrorScreen(
        directiveMessage = state.directiveMessage,
        loading = state.isLoading,
        errorMessage = state.errorMessage,
        onRetry = viewModel::loadData,
        onBackClick = onBackClick
    )
}

@Composable
fun SPagoErrorScreen(
    loading: Boolean,
    errorMessage: String? = null,
    directiveMessage: String = "Error",
    onBackClick: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = {
            CoopTopBar(
                title = "Error en Pago",
                leadingArrow = true,
                onBackClick = onBackClick
            )
        },
        containerColor = CoopTheme.colorScheme.surface
    ) { padding ->
        if(loading){
            LoadingScreen(
                message = "Cargando mensaje...",
                modifier = modifier.fillMaxSize()
            )
        } else if (errorMessage != null){
            ErrorScreen(
                message = errorMessage,
                onRetry = onRetry,
                modifier = modifier.fillMaxSize()
            )
        } else {
            Column(
                modifier = modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(6.dp))

                // Helper subtitle
                CoopText(
                    text = "Revisa el motivo y corrige para volver a enviar.",
                    style = CoopTheme.typography.bodyMedium,
                    color = CoopTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                // Reason card
                CoopOutlinedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        CoopText(
                            text = "Motivo del rechazo",
                            style = CoopTheme.typography.labelLarge,
                            color = CoopTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        CoopText(
                            text = directiveMessage,
                            style = CoopTheme.typography.bodyLarge,
                            color = CoopTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Acknowledge button
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                ){
                    CoopButton(
                        onClick = onBackClick,
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.DoneAll,
                                contentDescription = null,
                                tint = CoopTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            CoopText(text = "Entendido", color = CoopTheme.colorScheme.onPrimary)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}