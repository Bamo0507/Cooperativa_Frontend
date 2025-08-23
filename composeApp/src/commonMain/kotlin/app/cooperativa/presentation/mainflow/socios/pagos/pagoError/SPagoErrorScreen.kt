package app.cooperativa.presentation.mainflow.socios.pagos.pagoError

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cooperativa.presentation.utils.ErrorScreen
import app.cooperativa.presentation.utils.LoadingScreen
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopButton
import app.cooperativa.theme.components.CoopOutlinedCard
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
               Spacer(modifier = Modifier.padding(top = 18.dp))

                CoopOutlinedCard (
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        CoopText(
                            text = "Motivo",
                            style = CoopTheme.typography.labelLarge,
                            color = CoopTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(modifier = Modifier.padding(top = 4.dp))
                        CoopText(
                            text = directiveMessage,
                            style = CoopTheme.typography.bodyLarge,
                            color = CoopTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(top = 16.dp))

                CoopButton(onClick = onBackClick) {
                    CoopText(text = "Entendido")
                }
            }
        }
    }
}