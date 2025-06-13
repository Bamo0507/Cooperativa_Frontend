package app.cooperativa.presentation.mainflow.directiva.pagos.fineSelection

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.datetime.LocalDate
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import app.cooperativa.data.model.dto.FineType
import app.cooperativa.presentation.utils.LoadingScreen
import app.cooperativa.presentation.utils.ErrorScreen
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopButton
import app.cooperativa.theme.components.CoopOutlinedCard
import app.cooperativa.theme.components.CoopTopBar
import app.cooperativa.theme.components.CoopText

/**
 * Route Composable: handles loading/error and passes to screen when ready
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FineSelectionRoute(
    userId: Int,
    onBackClick: () -> Unit,
    onConfirm: () -> Unit,
    viewModel: FineViewModel = koinInject { parametersOf(userId) }
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
    onAmountChange: (detailId: Int, String) -> Unit,
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
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CoopText(
                text = "Usuario: ${state.userName}",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            state.fineDetails.forEach { detail ->
                CoopOutlinedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        CoopText(
                            text = when(detail.type) {
                                FineType.LOAN  -> "Préstamo"
                                FineType.QUOTA -> "Cuota"
                            },
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        CoopText(
                            text = detail.name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(start = 4.dp)
                        )

                        OutlinedTextField(
                            value = detail.amount,
                            onValueChange = { onAmountChange(detail.id, it) },
                            label = { Text("Monto") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                CoopButton(
                    onClick = onConfirmClick,
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}
