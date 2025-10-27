package app.cooperativa.presentation.mainflow.directiva.manager.fine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cooperativa.presentation.utils.ErrorScreen
import app.cooperativa.presentation.utils.LoadingScreen
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopButton
import app.cooperativa.theme.components.CoopDropdown
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopIconButton
import app.cooperativa.theme.components.CoopOutlinedTextField
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar
import app.cooperativa.utils.formatMoney
import org.koin.compose.koinInject

@Composable
fun DFineManagerRoute(
    onBackClick: () -> Unit,
    onBackWithConfettiClick: () -> Unit,
    viewModel: DFineManagerViewModel = koinInject()
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    DFineManagerScreen(
        state = state,
        loadMembers = viewModel::loadData,
        updateFineName = viewModel::updateFineName,
        updateFineAmount = viewModel::updateFineAmount,
        selectedAffiliate = viewModel::updateAffiliate,
        onSubmit = { viewModel.submitFine { onBackWithConfettiClick() } },
        onBackClick = onBackClick
    )
}

@Composable
fun DFineManagerScreen(
    loadMembers: () -> Unit,
    updateFineAmount: (amount: String) -> Unit,
    updateFineName: (name: String) -> Unit,
    selectedAffiliate: (affiliateName: String, affiliateId: String) -> Unit,
    onSubmit: () -> Unit,
    state: DFineManagerState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val canSubmit = state.affiliateId.isNotBlank() &&
            state.fineName.isNotBlank() &&
            state.fineAmount > 0f &&
            !state.isLoading

    Scaffold(
        topBar = {
            CoopTopBar(
                title = "Multa",
                leadingArrow = true,
                onBackClick = onBackClick,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        },
        containerColor = CoopTheme.colorScheme.surface
    ){ padding ->
        when {
            state.isLoading -> LoadingScreen(message = "Cargando socios…")
            state.errorMessage != null -> ErrorScreen(state.errorMessage!!, onRetry = loadMembers)
            else -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(CoopTheme.colorScheme.surface)
                        .padding(padding)
                        .padding(vertical = 6.dp, horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    LabeledField("Asociado") {
                        CoopDropdown(
                            items = state.memberOptions,
                            selectedItem = state.memberOptions.firstOrNull { it.userId == state.affiliateId },
                            onItemSelected = { member ->
                                selectedAffiliate(member.name, member.userId)
                            },
                            itemToString = { it.name },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = "Elige",
                            enableSearch = true,
                            showElevation = false,
                            radioSize = 20.dp,
                            optionMaxLines = 2
                        )
                    }

                    LabeledField("Razón de Multa") {
                        CoopOutlinedTextField(
                            value = state.fineName,
                            onValueChange = { input ->
                                val sanitized = input
                                    .replace("\n", " ")
                                    .replace("\r", " ")
                                    .take(20)
                                updateFineName(sanitized)
                            },
                            placeholder = { Text("Ingrese nombre") },
                            singleLine = true,
                            maxLines = 1,
                            unfocusedBorderColor = CoopTheme.colorScheme.primary
                        )
                    }

                    LabeledField("Monto de Multa") {
                        CoopOutlinedTextField(
                            value = state.fineAmountText,
                            onValueChange = updateFineAmount,
                            placeholder = { Text("0.00") },
                            prefix = { Text("Q ") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            singleLine = true,
                            digitsOnly = true,
                            allowDecimal = true,
                            allowNegative = false,
                            maxDecimalPlaces = 2,
                            unfocusedBorderColor = CoopTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Box(
                        contentAlignment = Alignment.BottomEnd,
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        CoopButton(
                            onClick = onSubmit,
                            enabled = canSubmit
                        ){
                            Row(
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CoopIcon(
                                    Icons.Default.Send,
                                    contentDescription = "Enviar",
                                    tint = CoopTheme.colorScheme.onPrimary
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                CoopText(
                                    text = "Registrar",
                                    color = CoopTheme.colorScheme.onPrimary,
                                    style = CoopTheme.typography.bodyMedium
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LabeledField(
    label: String,
    content: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        CoopText(
            label,
            style = CoopTheme.typography.bodyLarge,
            color = CoopTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        content()
    }
}
