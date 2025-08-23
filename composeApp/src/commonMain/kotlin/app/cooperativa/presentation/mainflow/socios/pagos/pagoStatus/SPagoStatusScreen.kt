package app.cooperativa.presentation.mainflow.socios.pagos.pagoStatus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.ChatBubble
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cooperativa.data.model.dto.Estados
import app.cooperativa.data.model.dto.PagosStatus
import app.cooperativa.presentation.utils.ErrorScreen
import app.cooperativa.presentation.utils.LoadingScreen
import app.cooperativa.presentation.utils.getStatusColor
import app.cooperativa.presentation.utils.getStatusText
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopIconButton
import app.cooperativa.theme.components.CoopOutlinedCard
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar
import org.koin.compose.koinInject

@Composable
fun SPagosStatusRoute(
    onAddPaymentClick: () -> Unit,
    onWatchError: (String) -> Unit,
    viewModel: SPagosStatusViewModel = koinInject()
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    SPagosStatusScreen(
        state = state,
        onRetry = viewModel::loadData,
        onWatchError = onWatchError,
        onAddPaymentClick = onAddPaymentClick
    )
}

@Composable
fun SPagosStatusScreen(
    state: SPagosStatusState,
    onWatchError: (String) -> Unit,
    onAddPaymentClick: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
){
    val payments = state.pagosStatus
    Scaffold(
        topBar = {
            CoopTopBar(title="Pagos")
        },
        containerColor = CoopTheme.colorScheme.surface,
        floatingActionButton = {
            if(!state.isLoading) {
                FloatingActionButton(
                    containerColor = CoopTheme.colorScheme.secondary,
                    contentColor = CoopTheme.colorScheme.onSecondary,
                    onClick = { onAddPaymentClick() },
                    content = {
                        CoopIcon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Presentar Pago"
                        )
                    }
                )
            }
        }
    ){ padding ->
        if(state.isLoading){
            LoadingScreen(
                message = "Cargando pagos..."
            )
        } else if(state.errorMessage != null){
            ErrorScreen(
                message = "Error cargando pagos",
                onRetry = onRetry
            )
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(CoopTheme.colorScheme.surface)
                    .padding(padding)
                    .padding(vertical = 6.dp, horizontal = 24.dp)
                    .padding(top=14.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // replace with state
                items(payments.size){idx ->
                    PagoStatusCard(
                        pagoId = payments[idx].pagoId,
                        nombrePago = payments[idx].nombrePago,
                        estado = payments[idx].estado,
                        onWatchError = onWatchError
                    )
                }
            }

        }
    }

}

@Composable
fun PagoStatusCard(
    nombrePago: String,
    estado: Estados,
    pagoId: String,
    onWatchError: (String) -> Unit,
    modifier: Modifier = Modifier
){
    var colorText = getStatusColor(estado)

    CoopOutlinedCard(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                verticalArrangement = Arrangement.SpaceAround
            ){
                CoopText(
                    text = nombrePago,
                    style = CoopTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = CoopTheme.colorScheme.onSurface
                )

                CoopText(
                    text = getStatusText(estado),
                    style = CoopTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = colorText
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if(estado == Estados.RECHAZADO){
                CoopIconButton(
                    onClick = { onWatchError(pagoId) },
                    modifier = Modifier.background(Color.Transparent)
                ){
                    CoopIcon(
                        Icons.Outlined.ErrorOutline,
                        contentDescription = "Error en Pago",
                        tint = CoopTheme.colorScheme.rejected,
                        modifier = Modifier.background(Color.Transparent)
                    )
                }
            }
        }

    }
}