package app.cooperativa.presentation.mainflow.directiva.pagos.pendingPaymentDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HighlightOff
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cooperativa.data.model.dto.Payment
import app.cooperativa.presentation.mainflow.directiva.pagos.pendingPaymentDetail.DPendingPayViewModel
import app.cooperativa.presentation.mainflow.directiva.pagos.pendingPaymentDetail.DPendingPayState
import app.cooperativa.presentation.utils.ErrorScreen
import app.cooperativa.presentation.utils.LoadingScreen
import app.cooperativa.presentation.utils.TicketFullScreenViewer
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopButton
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopOutlinedButton
import app.cooperativa.theme.components.CoopOutlinedCard
import app.cooperativa.theme.components.CoopOutlinedTextField
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar
import app.cooperativa.utils.formatMoney
import coil3.compose.AsyncImage
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun DPendingPayRoute(
    paymentId: String,
    onBackClick: () -> Unit,
    onBackWithConfettiClick: () -> Unit,
    viewModel: DPendingPayViewModel = koinInject { parametersOf(paymentId) }
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.navigateBack) {
        if (state.navigateBack) {
            onBackWithConfettiClick()
        }
    }

    when {
        state.isLoading -> {
            LoadingScreen(message = "Cargando pago…")
        }
        state.errorMessage != null -> {
            ErrorScreen(
                message = state.errorMessage!!,
                onRetry = { viewModel.loadPayment() }
            )
        }
        state.payment != null -> {
            DPendingPayScreen(
                ticketUrl = state.ticketUrl,
                ticketBytes = state.ticketBytes,
                showTicketViewer = state.showTicketViewer,
                payment = state.payment!!,
                isLoading = state.isLoading,
                commentInput = state.commentInput,
                showRejectDialog = state.showRejectDialog,
                onCloseTicketViewer = viewModel::closeTicketViewer,
                onOpenTicketViewer = viewModel::openTicketViewer,
                onCommentChange = viewModel::onCommentChange,
                onApprove = viewModel::onApprove,
                onReject = viewModel::onReject,
                onOpenRejectDialog = viewModel::openRejectDialog,
                onCloseRejectDialog = viewModel::closeRejectDialog,
                onBackClick = onBackClick
            )
        }
    }
}

@Composable
fun DPendingPayScreen(
    ticketUrl: String?,
    ticketBytes: ByteArray?,
    onCloseTicketViewer: () -> Unit,
    onOpenTicketViewer: () -> Unit,
    showTicketViewer: Boolean,
    isLoading: Boolean,
    payment: Payment,
    commentInput: String,
    showRejectDialog: Boolean,
    onCommentChange: (String) -> Unit,
    onApprove: () -> Unit,
    onReject: () -> Unit,
    onOpenRejectDialog: () -> Unit,
    onCloseRejectDialog: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CoopTopBar(
                title = payment.name,
                leadingArrow = true,
                onBackClick = onBackClick,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        },
        containerColor = CoopTheme.colorScheme.surface
    ) { padding ->
        Column(
            modifier = modifier
                .background(CoopTheme.colorScheme.surface)
                .padding(padding)
                .padding(vertical = 6.dp, horizontal = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            val hasTicket = (ticketUrl != null) || (ticketBytes != null)

            DPendingBasicInfoCard(payment)

            // Secciones derivadas de beingPayed
            val cuotas = payment.beingPayed.orEmpty().filter { it.modelType == "QUOTA" }
            cuotas.takeIf { it.isNotEmpty() }?.let { list ->
                DPendingSection(
                    title = "Cuotas",
                    values = list.mapIndexed { index, item ->
                        (index + 1).toString() to formatMoney(item.amount)
                    }
                )
            }

            val prestamos = payment.beingPayed.orEmpty().filter { it.modelType == "LOAN" }
            prestamos.takeIf { it.isNotEmpty() }?.let { list ->
                DPendingSection(
                    title = "Préstamos",
                    values = list.mapIndexed { index, item ->
                        (index + 1).toString() to formatMoney(item.amount)
                    }
                )
            }

            val multas = payment.beingPayed.orEmpty().filter { it.modelType == "FINE" }
            multas.takeIf { it.isNotEmpty() }?.let { list ->
                DPendingSection(
                    title = "Multas",
                    values = list.mapIndexed { index, item ->
                        (index + 1).toString() to formatMoney(item.amount)
                    }
                )
            }

            val aportes = payment.beingPayed.orEmpty().filter { it.modelType == "QUOTA" }
            aportes.takeIf { it.isNotEmpty() }?.let { list ->
                DPendingSection(
                    title = "Aportes",
                    values = list.mapIndexed { index, item ->
                        (index + 1).toString() to formatMoney(item.amount)
                    }
                )
            }

            // Imagen
            if (hasTicket) {
                AsyncImage(
                    model = ticketUrl ?: ticketBytes,
                    contentDescription = "Comprobante",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .padding(16.dp)
                        .background(CoopTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                        .border(1.dp, CoopTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    CoopIcon(
                        Icons.Default.Wallpaper,
                        contentDescription = "Imagen boleta",
                        tint = CoopTheme.colorScheme.primary,
                        modifier = Modifier.size(100.dp)
                    )
                }
            }

            // Boton de ver boleta
            CoopButton(
                onClick = { onOpenTicketViewer() },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CoopTheme.colorScheme.primary,
                )
            ){
                CoopText(
                    text = "Ver Boleta",
                    style = CoopTheme.typography.bodyLarge,
                    color = CoopTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botones de acción
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                CoopOutlinedButton(
                    onClick = onOpenRejectDialog,
                    shape = RoundedCornerShape(16.dp),
                    enabled = !showRejectDialog && !isLoading,
                ) {
                    CoopIcon(Icons.Default.Close, "Rechazar")
                    Spacer(Modifier.width(4.dp))
                    CoopText("Negar")
                }
                CoopButton(
                    onClick = onApprove,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CoopTheme.colorScheme.primary),
                    enabled = !isLoading
                ) {
                    CoopIcon(Icons.Default.Check, "Aprobar", tint = CoopTheme.colorScheme.onPrimary)
                    Spacer(Modifier.width(4.dp))
                    CoopText("Aprobar", color = CoopTheme.colorScheme.onPrimary)
                }
            }

            if (showRejectDialog) {
                RejectPaymentDialog(
                    comment = commentInput,
                    onCommentChange = onCommentChange,
                    onConfirm = onReject,
                    onDismiss = onCloseRejectDialog
                )
            }

            if (showTicketViewer) {
                TicketFullScreenViewer(
                    model = ticketUrl ?: ticketBytes,
                    onDismiss = onCloseTicketViewer
                )
            }
        }
    }
}

@Composable
private fun RejectPaymentDialog(
    comment: String,
    onCommentChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            CoopIcon(
                imageVector = Icons.Filled.HighlightOff,
                contentDescription = null,
                tint = CoopTheme.colorScheme.rejected,
                modifier = Modifier.size(36.dp)
            )
        },
        title = {
            CoopText(
                text = "Rechazar pago",
                style = CoopTheme.typography.titleMedium,
                color = CoopTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                CoopText(
                    text = "Escribe el motivo del rechazo para informar al socio.",
                    style = CoopTheme.typography.bodyMedium,
                    color = CoopTheme.colorScheme.onSurface
                )
                CoopOutlinedTextField(
                    value = comment,
                    onValueChange = onCommentChange,
                    placeholder = {
                        CoopText(
                            text = "Motivo de rechazo…",
                            style = CoopTheme.typography.bodyMedium,
                        )
                    },
                    isError = false,
                    singleLine = false,
                    maxLines = 4,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = CoopTheme.typography.bodyMedium,
                    // colores específicos para este diálogo
                    focusedBorderColor = CoopTheme.colorScheme.rejected,
                    unfocusedBorderColor = CoopTheme.colorScheme.rejected.copy(alpha = 0.5f),
                    cursorColor = CoopTheme.colorScheme.rejected
                )
            }
        },
        dismissButton = {
            CoopOutlinedButton(onClick = onDismiss) {
                CoopText(
                    text = "Cancelar",
                    style = CoopTheme.typography.bodyMedium,
                )
            }
        },
        confirmButton = {
            CoopButton(
                onClick = onConfirm,
                enabled = comment.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CoopTheme.colorScheme.rejected.copy(0.9f),
                    contentColor = CoopTheme.colorScheme.onPrimary,
                    disabledContainerColor = CoopTheme.colorScheme.rejected.copy(alpha = 0.60f),
                    disabledContentColor = CoopTheme.colorScheme.onPrimary.copy(alpha = 0.65f)
                )
            ) {
                CoopText(
                    text = "Enviar",
                    color = Color.White,
                    style = CoopTheme.typography.bodyMedium,
                )
            }
        },
        containerColor = CoopTheme.colorScheme.surface
    )
}

@Composable
private fun DPendingBasicInfoCard(payment: Payment) {
    val totalFromItems = payment.beingPayed.orEmpty().sumOf { it.amount.toDouble() }.toFloat()

    CoopOutlinedCard(modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            InfoRow("Nombre del pago", payment.name)
            InfoRow("Presentado por",  payment.presentedByName)
            InfoRow("Fecha presentada", payment.paymentDate)
            InfoRow("Nº de boleta",    payment.ticketNum)
            InfoRow("Nº de cuenta",    payment.accountNum)

            Spacer(Modifier.height(4.dp))
            InfoRow(
                label = "Monto total",
                value = if (totalFromItems > 0f) formatMoney(totalFromItems) else formatMoney(payment.totalAmount),
                valueColor = CoopTheme.colorScheme.onSecondary
            )
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    valueColor: Color = CoopTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        CoopText(
            text = label,
            fontWeight = FontWeight.Bold,
            color = CoopTheme.colorScheme.onSurface.copy(alpha = 0.90f),
            modifier = Modifier.weight(1f)
        )
        CoopText(
            text = value,
            color = valueColor,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun DPendingSection(
    title: String,
    values: List<Pair<String, String>>
) {
    CoopOutlinedCard(modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)) {
        Column(Modifier.padding(16.dp)) {
            CoopText(text = title, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            values.forEach { (label, value) ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CoopText(label)
                    CoopText(value, color = CoopTheme.colorScheme.onSecondary)
                }
            }
        }
    }
}
