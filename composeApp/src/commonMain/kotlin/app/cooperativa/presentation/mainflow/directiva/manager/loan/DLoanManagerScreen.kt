package app.cooperativa.presentation.mainflow.directiva.manager.loan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cooperativa.presentation.utils.ErrorScreen
import app.cooperativa.presentation.utils.LoadingScreen
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.*
import org.koin.compose.koinInject

@Composable
fun DLoanManagerRoute(
    onBackClick: () -> Unit,
    onBackWithConfettiClick: () -> Unit,
    viewModel: DLoanManagerViewModel = koinInject()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    DLoanManagerScreen(
        state = state,
        loadMembers = viewModel::loadData,
        updateLoanReason = viewModel::updateLoanReason,
        updateAmount = viewModel::updateAmount,
        updateInterest = viewModel::updateInterest,
        updateTotalQuota = viewModel::updateTotalQuota,
        selectedAffiliate = viewModel::updateAffiliate,
        onSubmit = {
            viewModel.submitLoan()
            onBackWithConfettiClick()
        },
        onBackClick = onBackClick
    )
}

@Composable
fun DLoanManagerScreen(
    state: DLoanManagerState,
    loadMembers: () -> Unit,
    updateLoanReason: (String) -> Unit,
    updateAmount: (String) -> Unit,
    updateInterest: (String) -> Unit,
    updateTotalQuota: (String) -> Unit,
    selectedAffiliate: (affiliateName: String, affiliateId: String) -> Unit,
    onSubmit: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val formValid =
        state.affiliateId.isNotBlank() &&
                state.loanReason.isNotBlank() &&
                state.amount > 0f &&
                state.interest >= 0f &&
                state.totalQuota > 0

    Scaffold(
        topBar = {
            CoopTopBar(
                title = "Préstamo",
                leadingArrow = true,
                onBackClick = onBackClick,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        },
        containerColor = CoopTheme.colorScheme.surface
    ) { padding ->
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
                            onItemSelected = { member -> selectedAffiliate(member.name, member.userId) },
                            itemToString = { it.name },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = "Elige",
                            enableSearch = true,
                            showElevation = false,
                            radioSize = 20.dp,
                            optionMaxLines = 2
                        )
                    }

                    LabeledField("Motivo Préstamo") {
                        CoopOutlinedTextField(
                            value = state.loanReason,
                            onValueChange = { input ->
                                val sanitized = input.replace("\n", " ").replace("\r", " ").take(20)
                                updateLoanReason(sanitized)
                            },
                            placeholder = { Text("Escribe el motivo") },
                            singleLine = true,
                            maxLines = 1,
                            unfocusedBorderColor = CoopTheme.colorScheme.primary
                        )
                    }

                    LabeledField("Monto") {
                        CoopOutlinedTextField(
                            value = state.amountText,
                            onValueChange = updateAmount,
                            placeholder = { Text("0.00") },
                            prefix = { Text(text = "Q ", color = CoopTheme.colorScheme.onSurface) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            singleLine = true,
                            digitsOnly = true,
                            allowDecimal = true,
                            allowNegative = false,
                            maxDecimalPlaces = 2,
                            unfocusedBorderColor = CoopTheme.colorScheme.primary
                        )
                    }

                    LabeledField("Interés (%)") {
                        CoopOutlinedTextField(
                            value = state.interestText,
                            onValueChange = updateInterest,
                            placeholder = { Text("0.00") },
                            prefix = { Text("% ", color = CoopTheme.colorScheme.onSurface) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            singleLine = true,
                            digitsOnly = true,
                            allowDecimal = true,
                            allowNegative = false,
                            maxDecimalPlaces = 2,
                            unfocusedBorderColor = CoopTheme.colorScheme.primary
                        )
                    }

                    LabeledField("Cuotas") {
                        CoopOutlinedTextField(
                            value = state.totalQuotaText,
                            onValueChange = updateTotalQuota,
                            placeholder = { Text("0") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            digitsOnly = true,
                            allowDecimal = false,
                            allowNegative = false,
                            unfocusedBorderColor = CoopTheme.colorScheme.primary
                        )
                    }

                    Spacer(Modifier.weight(1f))

                    Box(
                        contentAlignment = Alignment.BottomEnd,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                    ) {
                        CoopButton(
                            onClick = onSubmit,
                            enabled = formValid && !state.isLoading
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CoopIcon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = "Enviar",
                                    tint = CoopTheme.colorScheme.onPrimary
                                )
                                Spacer(Modifier.width(12.dp))
                                Text(
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
            fontWeight = FontWeight.Bold,
            color = CoopTheme.colorScheme.onSurface
        )
        content()
    }
}