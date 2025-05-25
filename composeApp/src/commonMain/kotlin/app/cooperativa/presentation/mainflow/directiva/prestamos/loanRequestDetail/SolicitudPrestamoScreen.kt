package app.cooperativa.presentation.mainflow.directiva.prestamos.loanRequestDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cooperativa.data.localdb.SolicitudPrestamoMockData
import app.cooperativa.data.model.dto.SolicitudPrestamo
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopButton
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopOutlinedButton
import app.cooperativa.theme.components.CoopOutlinedCard
import app.cooperativa.theme.components.CoopOutlinedTextField
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar
import app.cooperativa.utils.formatMoney
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun SolicitudPrestamoRoute(
    solicitudId: Int,
    onBackClick: () -> Unit,
    viewModel: SolicitudPrestamoViewModel = koinInject( parameters = { parametersOf(solicitudId) })
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    state.prestamo?.let { prestamo ->
        SolicitudPrestamoScreen(
            prestamo = prestamo,
            interestInput = state.interestInput,
            commentsInput = state.commentsInput,
            onInterestChange = viewModel::onInterestChange,
            onCommentsChange = viewModel::onCommentsChange,
            onApprove = viewModel::onApprove,
            onReject = viewModel::onReject,
            onBackClick = onBackClick
        )
    }
}

@Composable
fun SolicitudPrestamoScreen(
    prestamo: SolicitudPrestamo,
    interestInput: Float,
    commentsInput: String,
    onInterestChange: (Float) -> Unit,
    onCommentsChange: (String) -> Unit,
    onApprove: () -> Unit,
    onReject: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CoopTopBar(
                title = prestamo.nombrePrestamo,
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
                .padding(vertical = 6.dp, horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Solicitante
            CoopText(
                text = prestamo.nombreSolicitante,
                fontWeight = FontWeight.Bold,
                style = CoopTheme.typography.bodyLarge,
                color = CoopTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Monto
            CoopOutlinedCard(
                containerColor = CoopTheme.colorScheme.primary,
                elevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    CoopText(
                        text = "Monto",
                        fontWeight = FontWeight.Bold,
                        style = CoopTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    CoopText(
                        text = formatMoney(prestamo.montoTotal),
                        style = CoopTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Motivo
            CoopOutlinedCard(elevation = 0.dp) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    CoopText(
                        text = "Motivo",
                        fontWeight = FontWeight.Bold,
                        style = CoopTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    CoopText(
                        text = prestamo.motivo,
                        style = CoopTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Codeudores
            if (!prestamo.codeudores.isNullOrEmpty()) {
                CoopOutlinedCard(elevation = 0.dp) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        CoopText(
                            text = "Codeudores",
                            fontWeight = FontWeight.Bold,
                            style = CoopTheme.typography.bodyLarge,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        prestamo.codeudores.forEach { codeudor ->
                            CoopText(
                                text = codeudor.nombre,
                                fontWeight = FontWeight.Bold,
                                style = CoopTheme.typography.bodyMedium
                            )
                            CoopText(text = codeudor.dpi, style = CoopTheme.typography.bodyMedium)
                            CoopText(text = codeudor.nit, style = CoopTheme.typography.bodyMedium)
                            CoopText(text = codeudor.correo, style = CoopTheme.typography.bodyMedium)
                            CoopText(text = codeudor.telefono, style = CoopTheme.typography.bodyMedium)
                            CoopText(text = codeudor.direccion, style = CoopTheme.typography.bodyMedium)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Interés (%) Input
            CoopOutlinedTextField(
                value = interestInput.toString(),
                onValueChange = { input -> input.toFloatOrNull()?.let(onInterestChange) },
                label = { CoopText(text = "Interés (%)") },
                placeholder = { CoopText(text = "%") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Comentarios Input
            CoopText(
                text = "Comentarios",
                style = CoopTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            CoopOutlinedTextField(
                value = commentsInput,
                onValueChange = onCommentsChange,
                modifier = Modifier
                    .border(1.dp, CoopTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                    .height(128.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Acciones Aceptar/Rechazar
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CoopOutlinedButton(
                    onClick = onReject,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.height(48.dp)
                ) {
                    CoopIcon(Icons.Default.Close, contentDescription = "Rechazar")
                    Spacer(modifier = Modifier.width(4.dp))
                    CoopText(text = "Negar", style = CoopTheme.typography.bodyLarge)
                }
                CoopButton(
                    onClick = onApprove,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CoopTheme.colorScheme.primary,
                        contentColor = CoopTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.height(48.dp)
                ) {
                    CoopIcon(Icons.Default.Check, contentDescription = "Aprobar")
                    Spacer(modifier = Modifier.width(4.dp))
                    CoopText(text = "Aprobar", style = CoopTheme.typography.bodyLarge)
                }
            }
        }
    }
}
