package app.cooperativa.presentation.mainflow.socios.pagos.pagoStatus

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cooperativa.data.model.dto.Estados
import app.cooperativa.presentation.utils.ErrorScreen
import app.cooperativa.presentation.utils.LoadingScreen
import app.cooperativa.presentation.utils.getStatusColor
import app.cooperativa.presentation.utils.getStatusText
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopOutlinedButton
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
                items(payments.size){idx ->
                    PagoStatusCard(
                        pagoId = payments[idx].pagoId,
                        nombrePago = payments[idx].nombrePago,
                        estado = payments[idx].estado,
                        dateOfPayment = payments[idx].dateOfPayment,
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
    dateOfPayment: String,
    onWatchError: (String) -> Unit,
    modifier: Modifier = Modifier
){
    var colorText = getStatusColor(estado)

    var showApprovedDialog by remember { mutableStateOf(false) }
    var showPendingDialog by remember { mutableStateOf(false) }

    CoopOutlinedCard(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    when (estado) {
                        Estados.REJECTED -> onWatchError(pagoId)
                        Estados.ACCEPTED -> showApprovedDialog = true
                        Estados.ON_REVISION -> showPendingDialog = true
                    }
                }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                CoopText(
                    text = nombrePago,
                    style = CoopTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = CoopTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                CoopText(
                    text = getStatusText(estado),
                    style = CoopTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = colorText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                CoopText(
                    text = dateOfPayment,
                    style = CoopTheme.typography.bodySmall,
                    color = CoopTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            when (estado) {
                Estados.REJECTED -> {
                    Box(
                        modifier = Modifier.size(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CoopIcon(
                            Icons.Outlined.ErrorOutline,
                            contentDescription = "Error en Pago",
                            tint = CoopTheme.colorScheme.rejected,
                            modifier = Modifier.size(24.dp).background(Color.Transparent)
                        )
                    }
                }
                Estados.ACCEPTED -> {
                    Box(
                        modifier = Modifier.size(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CoopIcon(
                            Icons.Outlined.CheckCircle,
                            contentDescription = "Pago aprobado",
                            tint = CoopTheme.colorScheme.approved,
                            modifier = Modifier.size(24.dp).background(Color.Transparent)
                        )
                    }
                }
                Estados.ON_REVISION -> {
                    Box(
                        modifier = Modifier.size(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CoopIcon(
                            Icons.Outlined.Schedule,
                            contentDescription = "Pago en revisión",
                            tint = CoopTheme.colorScheme.pending,
                            modifier = Modifier.size(24.dp).background(Color.Transparent)
                        )
                    }
                }
            }
        }
        if (showApprovedDialog) {
            ApprovedDialog(onDismiss = { showApprovedDialog = false })
        }
        if (showPendingDialog) {
            PendingDialog(onDismiss = { showPendingDialog = false })
        }
    }
}

@Composable
private fun ApprovedDialog(onDismiss: () -> Unit) {
    SimpleStatusDialog(
        icon = Icons.Outlined.CheckCircle,
        iconTint = CoopTheme.colorScheme.approved,
        title = "Pago Aprobado",
        message = "Tu pago ha sido aprobado.",
        onDismiss = onDismiss
    )
}

@Composable
private fun PendingDialog(onDismiss: () -> Unit) {
    SimpleStatusDialog(
        icon = Icons.Outlined.Schedule,
        iconTint = CoopTheme.colorScheme.pending,
        title = "Pago en revisión",
        message = "Tu pago está en revisión. Te notificaremos cuando el estado cambie.",
        onDismiss = onDismiss
    )
}

@Composable
private fun SimpleStatusDialog(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            CoopIcon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(36.dp)
            )
        },
        title = {
            CoopText(
                text = title,
                style = CoopTheme.typography.titleMedium,
                color = CoopTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            CoopText(
                text = message,
                style = CoopTheme.typography.bodyMedium,
                color = CoopTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CoopOutlinedButton(
                    onClick = onDismiss,
                    border = BorderStroke(1.dp, iconTint)
                ) {
                    CoopText(
                        text = "Entendido",
                        color = CoopTheme.colorScheme.onSurface,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        },
        containerColor = CoopTheme.colorScheme.surface
    )
}