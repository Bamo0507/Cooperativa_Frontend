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
    viewModel: DFineManagerViewModel = koinInject()
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    DFineManagerScreen(
        state = state,
        loadMembers = viewModel::loadData,
        updateFineName = viewModel::updateFineName,
        updateFineAmount = viewModel::updateFineAmount,
        selectedAffiliate = viewModel::updateAffiliate,
        onBackClick = onBackClick
    )
}

@Composable
fun DFineManagerScreen(
    loadMembers: () -> Unit,
    updateFineAmount: (amount: String) -> Unit,
    updateFineName: (name: String) -> Unit,
    selectedAffiliate: (affiliateName: String, affiliateId: Int) -> Unit,
    state: DFineManagerState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){
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
        if(state.isLoading){
            LoadingScreen(
                message = "Cargando socios..."
            )
        } else if (state.errorMessage != null){
            ErrorScreen(
                message = state.errorMessage!!,
                onRetry = loadMembers
            )
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(CoopTheme.colorScheme.surface)
                    .padding(padding)
                    .padding(vertical = 6.dp, horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ){
                CoopDropdown(
                    items = state.memberOptions,
                    selectedItem = state.memberOptions
                        .firstOrNull { it.usuarioId == state.affiliateId },
                    onItemSelected = { member ->
                        selectedAffiliate(member.name, member.usuarioId)
                    },
                    itemToString = { it.name },
                    label = { CoopText("Selecciona...") },
                    placeholder = { CoopText("Elige") }
                )

                CoopOutlinedTextField(
                    value = state.fineName,
                    onValueChange = { input ->
                        val sanitized = input
                            .replace("\n", " ")
                            .replace("\r", " ")
                            .take(20)
                        updateFineName(sanitized)
                    },
                    label = { Text("Nombre de Multa") },
                    placeholder = { Text("Ingrese nombre") },
                    isError = false,
                    singleLine = true,
                    maxLines = 1,
                    modifier = Modifier.padding(vertical = 6.dp)
                )

                CoopOutlinedTextField(
                    value = state.fineAmountText,
                    onValueChange = updateFineAmount,
                    label = { Text("Monto de Multa") },
                    placeholder = { Text("0.00") },
                    prefix = { Text("Q ") },
                    isError = false,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier.padding(vertical = 6.dp),
                    digitsOnly = true,
                    allowDecimal = true,
                    allowNegative = false,
                    maxDecimalPlaces = 2
                )

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                ){
                    CoopButton(
                        onClick = { /*TODO*/ },
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
