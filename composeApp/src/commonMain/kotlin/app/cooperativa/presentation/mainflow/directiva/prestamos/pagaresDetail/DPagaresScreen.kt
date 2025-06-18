package app.cooperativa.presentation.mainflow.directiva.prestamos.pagaresDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cooperativa.data.model.dto.Pagare
import app.cooperativa.presentation.utils.ErrorScreen
import app.cooperativa.presentation.utils.LoadingScreen
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.*
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun DPagaresRoute(
    pagareId: Int,
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
    viewModel: DPagaresViewModel = koinInject { parametersOf(pagareId) }
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        state.isLoading -> LoadingScreen(message = "Cargando pagaré…")
        state.errorMessage != null -> ErrorScreen(
            message = state.errorMessage!!,
            onRetry = viewModel::loadPagare
        )
        else -> DPagareScreen(
            pagare            = state.pagare!!,
            comments          = state.commentsInput,
            onCommentsChange  = viewModel::onCommentsChange,
            onApprove         = { viewModel.onApprove(); onConfirmClick() },
            onReject          = { viewModel.onReject(); onConfirmClick() },
            onDownloadPdf     = { /* TODO */ },
            onBackClick       = onBackClick
        )
    }
}

@Composable
fun DPagareScreen(
    pagare: Pagare,
    comments: String,
    onCommentsChange: (String) -> Unit,
    onApprove: () -> Unit,
    onReject: () -> Unit,
    onDownloadPdf: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CoopTopBar(
                title = "Revisión de Pagaré",
                leadingArrow = true,
                onBackClick  = onBackClick
            )
        },
        containerColor = CoopTheme.colorScheme.surface
    ) { padding ->
        Column(
            modifier = modifier
                .background(CoopTheme.colorScheme.surface)
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // — Solicitante —
            CoopOutlinedCard(
                elevation = 2.dp,
                shape     = RoundedCornerShape(16.dp)
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    CoopText("Solicitante", fontWeight = CoopTheme.typography.bodyLarge.fontWeight)
                    CoopText(pagare.solicitante, style = CoopTheme.typography.bodyMedium)
                }
            }

            // — Nombre de Préstamo —
            CoopOutlinedCard(
                elevation = 2.dp,
                shape     = RoundedCornerShape(16.dp)
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    CoopText("Préstamo", fontWeight = CoopTheme.typography.bodyLarge.fontWeight)
                    CoopText(pagare.nombrePrestamo, style = CoopTheme.typography.bodyMedium)
                }
            }

            // — Estado —
            CoopOutlinedCard(
                elevation = 2.dp,
                shape     = RoundedCornerShape(16.dp)
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    CoopText("Estado", fontWeight = CoopTheme.typography.bodyLarge.fontWeight)
                    CoopText(pagare.estado.name, style = CoopTheme.typography.bodyMedium)
                }
            }

            // — Descargar PDF —
            CoopOutlinedButton(
                onClick = onDownloadPdf,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CoopIcon(Icons.Default.Download, contentDescription = "Descargar")
                Spacer(Modifier.width(8.dp))
                CoopText("Descargar PDF")
            }

            // — Comentarios de rechazo —
            CoopText(
                text = "Comentarios",
                fontWeight = CoopTheme.typography.bodyLarge.fontWeight
            )
            CoopOutlinedTextField(
                value = comments,
                onValueChange = onCommentsChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp)
                    .border(
                        width = 1.dp,
                        color = CoopTheme.colorScheme.surface
                    ),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(Modifier.height(12.dp))

            // — Acciones —
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CoopOutlinedButton(
                    onClick = onReject,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    CoopIcon(Icons.Default.Close, contentDescription = "Rechazar")
                    Spacer(Modifier.width(6.dp))
                    CoopText("Rechazar")
                }

                CoopButton(
                    onClick = onApprove,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CoopTheme.colorScheme.primary,
                        contentColor = CoopTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    CoopIcon(Icons.Default.Check, contentDescription = "Aprobar")
                    Spacer(Modifier.width(6.dp))
                    CoopText("Aprobar")
                }
            }
        }
    }
}