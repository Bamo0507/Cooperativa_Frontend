package app.cooperativa.presentation.mainflow.directiva.pagos.paymentDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cooperativa.data.model.dto.Payment
import app.cooperativa.presentation.mainflow.directiva.pagos.paymentDetail.DPaidPayViewModel
import app.cooperativa.presentation.utils.ErrorScreen
import app.cooperativa.presentation.utils.LoadingScreen
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.*
import app.cooperativa.theme.utils.dateToString
import app.cooperativa.utils.formatMoney
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun DPaidPayRoute(
    paymentId: Int,
    onBackClick: () -> Unit,
    viewModel: DPaidPayViewModel = koinInject { parametersOf(paymentId) }
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        state.isLoading -> LoadingScreen(message = "Cargando pago…")
        state.errorMessage != null -> ErrorScreen(
            message = state.errorMessage!!,
            onRetry = viewModel::loadPayment
        )
        state.payment != null -> DPaidPayScreen(
            payment = state.payment!!,
            onBackClick = onBackClick
        )
    }
}

@Composable
fun DPaidPayScreen(
    payment: Payment,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CoopTopBar(
                title = payment.paymentName,
                leadingArrow = true,
                onBackClick = onBackClick
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
                DPaidSection(
                    title  = "Cuotas",
                    values = list.map { dateToString(it.date) to formatMoney(it.amount) }
                )
            }
            // Préstamos
            payment.loanPayments.orEmpty().takeIf { it.isNotEmpty() }?.let { list ->
                DPaidSection(
                    title  = "Préstamos",
                    values = list.map { dateToString(it.date) to formatMoney(it.amountPayed) }
                )
            }
            // Multas
            payment.finePayments.orEmpty().takeIf { it.isNotEmpty() }?.let { list ->
                DPaidSection(
                    title  = "Multas",
                    values = list.map { it.fineName to formatMoney(it.amount) }
                )
            }
            // Aportes
            payment.contributionPayments.orEmpty().takeIf { it.isNotEmpty() }?.let { list ->
                DPaidSection(
                    title  = "Aportes",
                    values = list.map { it.user to formatMoney(it.amount) }
                )
            }

            // Boleta (imagen simulada)
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
    }
}

@Composable
private fun DPaidSection(
    title: String,
    values: List<Pair<String, String>>
) {
    CoopOutlinedCard(
        modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            CoopText(text = title, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            values.forEach { (label, value) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CoopText(label)
                    CoopText(value, color = CoopTheme.colorScheme.onSecondary)
                }
            }
        }
    }
}
