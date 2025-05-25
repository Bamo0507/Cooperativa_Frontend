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
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cooperativa.data.model.dto.Payment
import app.cooperativa.presentation.mainflow.directiva.pagos.pendingPaymentDetail.DPendingPayViewModel
import app.cooperativa.presentation.mainflow.directiva.pagos.pendingPaymentDetail.DPendingPayState
import app.cooperativa.presentation.utils.ErrorScreen
import app.cooperativa.presentation.utils.LoadingScreen
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopButton
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopOutlinedButton
import app.cooperativa.theme.components.CoopOutlinedCard
import app.cooperativa.theme.components.CoopOutlinedTextField
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar
import app.cooperativa.theme.utils.dateToString
import app.cooperativa.utils.formatMoney
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun DPendingPayRoute(
    paymentId: Int,
    onBackClick: () -> Unit,
    viewModel: DPendingPayViewModel = koinInject { parametersOf(paymentId) }
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

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
                payment = state.payment!!,
                commentInput = state.commentInput,
                onCommentChange = viewModel::onCommentChange,
                onApprove = viewModel::onApprove,
                onReject = viewModel::onReject,
                onBackClick = onBackClick
            )
        }
    }
}

@Composable
fun DPendingPayScreen(
    payment: Payment,
    commentInput: String,
    onCommentChange: (String) -> Unit,
    onApprove: () -> Unit,
    onReject: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CoopTopBar(
                title = payment.paymentName,
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
            // Cuotas
            payment.quotas.orEmpty().takeIf { it.isNotEmpty() }?.let { list ->
                DPendingSection(
                    title = "Cuotas",
                    values = list.map { dateToString(it.date) to formatMoney(it.amount) }
                )
            }
            // Préstamos
            payment.loanPayments.orEmpty().takeIf { it.isNotEmpty() }?.let { list ->
                DPendingSection(
                    title = "Préstamos",
                    values = list.map { dateToString(it.date) to formatMoney(it.amountPayed) }
                )
            }
            // Multas
            payment.finePayments.orEmpty().takeIf { it.isNotEmpty() }?.let { list ->
                DPendingSection(
                    title = "Multas",
                    values = list.map { it.fineName to formatMoney(it.amount) }
                )
            }
            // Aportes
            payment.contributionPayments.orEmpty().takeIf { it.isNotEmpty() }?.let { list ->
                DPendingSection(
                    title = "Aportes",
                    values = list.map { it.user to formatMoney(it.amount) }
                )
            }

            // Imagen
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

            // Boton de ver boleta
            CoopButton(
                onClick = {
                    /* TODO */
                },
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


            // Comentarios
            CoopText(
                text = "Comentarios",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                color = CoopTheme.colorScheme.onSurface
            )
            CoopOutlinedTextField(
                value = commentInput,
                onValueChange = onCommentChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(1.dp, CoopTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                    .height(128.dp)
            )

            // Botones de acción
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                CoopOutlinedButton(onClick = onReject, shape = RoundedCornerShape(16.dp)) {
                    CoopIcon(Icons.Default.Close, "Rechazar")
                    Spacer(Modifier.width(4.dp))
                    CoopText("Negar")
                }
                CoopButton(onClick = onApprove, shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(containerColor = CoopTheme.colorScheme.primary)) {
                    CoopIcon(Icons.Default.Check, "Aprobar", tint = CoopTheme.colorScheme.onPrimary)
                    Spacer(Modifier.width(4.dp))
                    CoopText("Aprobar", color = CoopTheme.colorScheme.onPrimary)
                }
            }
        }
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
