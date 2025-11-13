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
import app.cooperativa.utils.formatMoney
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import app.cooperativa.presentation.utils.TicketFullScreenViewer
import coil3.compose.AsyncImage

@Composable
fun DPaidPayRoute(
    paymentId: String,
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
            onBackClick = onBackClick,
            ticketUrl = state.ticketUrl,
            ticketBytes = state.ticketBytes,
            showTicketViewer = state.showTicketViewer,
            onCloseTicketViewer = viewModel::closeTicketViewer,
            onOpenTicketViewer = viewModel::openTicketViewer,
        )
    }
}

@Composable
fun DPaidPayScreen(
    ticketUrl: String?,
    ticketBytes: ByteArray?,
    onCloseTicketViewer: () -> Unit,
    onOpenTicketViewer: () -> Unit,
    showTicketViewer: Boolean,
    payment: Payment,
    onBackClick: () -> Unit,
    onViewTicket: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CoopTopBar(
                title = payment.name,
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
            val hasTicket = (ticketUrl != null) || (ticketBytes != null)

            // Resumen del pago
            CoopOutlinedCard(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CoopText(
                            text = payment.presentedByName,
                            style = CoopTheme.typography.bodyLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = CoopTheme.colorScheme.onSurface
                        )
                        CoopText(
                            text = formatMoney(payment.totalAmount),
                            style = CoopTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = CoopTheme.colorScheme.onSurface
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CoopText("Fecha")
                        CoopText(payment.paymentDate, color = CoopTheme.colorScheme.onSecondary)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CoopText("Cuenta")
                        CoopText(payment.accountNum, color = CoopTheme.colorScheme.onSecondary)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CoopText("Boleta")
                        CoopText(payment.ticketNum, color = CoopTheme.colorScheme.onSecondary)
                    }
                }
            }

            // Detalle: agrupado por tipo de pago a partir de beingPayed
            val payed = payment.beingPayed.orEmpty()

            // Cuotas (AFILIADO)
            val cuotas = payed.filter { it.modelType == "QUOTA" }
            if (cuotas.isNotEmpty()) {
                DPaidSection(
                    title = "Cuotas",
                    values = cuotas.mapIndexed { index, item ->
                        (index + 1).toString() to formatMoney(item.amount.toFloat())
                    }
                )
            }

            // Préstamos (PRESTAMO)
            val prestamos = payed.filter { it.modelType == "LOAN" }
            if (prestamos.isNotEmpty()) {
                DPaidSection(
                    title = "Préstamos",
                    values = prestamos.mapIndexed { index, item ->
                        (index + 1).toString() to formatMoney(item.amount.toFloat())
                    }
                )
            }

            // Multas (FINE)
            val multas = payed.filter { it.modelType == "FINE" }
            if (multas.isNotEmpty()) {
                DPaidSection(
                    title = "Multas",
                    values = multas.mapIndexed { index, item ->
                        (index + 1).toString() to formatMoney(item.amount.toFloat())
                    }
                )
            }

            // Aportes de Capital (CAPITAL)
            val aportes = payed.filter { it.modelType == "QUOTA" }
            if (aportes.isNotEmpty()) {
                DPaidSection(
                    title = "Aportes de Capital",
                    values = aportes.mapIndexed { index, item ->
                        (index + 1).toString() to formatMoney(item.amount.toFloat())
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
