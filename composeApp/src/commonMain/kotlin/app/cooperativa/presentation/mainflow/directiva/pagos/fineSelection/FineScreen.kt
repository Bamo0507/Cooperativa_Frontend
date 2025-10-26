package app.cooperativa.presentation.mainflow.directiva.pagos.fineSelection

import app.cooperativa.utils.formatMoney
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import app.cooperativa.presentation.utils.LoadingScreen
import app.cooperativa.presentation.utils.ErrorScreen
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopButton
import app.cooperativa.theme.components.CoopTopBar
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopOutlinedTextField

/**
 * Route Composable: handles loading/error and passes to screen when ready
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FineSelectionRoute(
    accessKey: String,
    onBackClick: () -> Unit,
    onConfirm: () -> Unit,
    viewModel: FineViewModel = koinInject { parametersOf(accessKey) }
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        state.isLoading -> LoadingScreen(message = "Cargando moras…")
        state.errorMessage != null -> ErrorScreen(
            message = state.errorMessage!!,
            onRetry = viewModel::loadFines
        )
        else -> FineSelectionScreen(
            state = state,
            onBackClick    = onBackClick,
            onConfirmClick = { viewModel.onConfirmClick(onConfirm) },
            onAmountChange = viewModel::onAmountChange
        )
    }
}

/**
 * Screen Composable: pure UI given loaded state
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FineSelectionScreen(
    state: FineSelectionState,
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
    onAmountChange: (fineKey: String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CoopTopBar(
                title = "Editar Moras",
                leadingArrow = true,
                onBackClick  = onBackClick
            )
        },
        containerColor = CoopTheme.colorScheme.surface
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(vertical = 20.dp, horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
//            CoopText(
//                text = "Usuario: ${state.userName}",
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.titleMedium,
//                color = CoopTheme.colorScheme.onSurface
//            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(CoopTheme.colorScheme.secondary.copy(alpha = 0.12f))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                CoopText(
                    text = "Multas (${state.fineDetails.size})",
                    style = MaterialTheme.typography.labelLarge,
                    color = CoopTheme.colorScheme.onSecondary
                )
            }

            state.fineDetails.forEach { detail ->
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CoopText(
                        text = detail.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = CoopTheme.colorScheme.onSurface
                    )

                    CoopOutlinedTextField(
                        value = detail.amount,
                        onValueChange = { onAmountChange(detail.id, it) },
                        label = { Text("Monto") },
                        prefix = { Text("Q ") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        digitsOnly = true,
                        allowDecimal = true,
                        allowNegative = false,
                        maxDecimalPlaces = 2,
                    )


                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Resumen de totales
            val total = state.fineDetails.sumOf { it.amount.toDoubleOrNull() ?: 0.0 }.toFloat()
            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CoopText(
                    text = "Resumen",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = CoopTheme.colorScheme.onSurface
                )
                CoopText(
                    text = if (state.fineDetails.size == 1)
                        "Total: ${formatMoney(total)}  (${state.fineDetails.size} multa)"
                    else
                        "Total: ${formatMoney(total)}  (${state.fineDetails.size} multas)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = CoopTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                CoopButton(
                    onClick = onConfirmClick,
                    modifier = Modifier
                ) {
                    Icon(imageVector = Icons.Filled.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Guardar")
                }
            }
        }
    }
}
