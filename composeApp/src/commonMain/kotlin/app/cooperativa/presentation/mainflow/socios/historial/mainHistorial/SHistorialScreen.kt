package app.cooperativa.presentation.mainflow.socios.historial.mainHistorial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cooperativa.data.model.dto.Loan
import app.cooperativa.presentation.utils.ErrorScreen
import app.cooperativa.presentation.utils.LoadingScreen
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopOutlinedButton
import app.cooperativa.theme.components.CoopOutlinedCard
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar
import app.cooperativa.utils.formatMoney
import org.koin.compose.koinInject

@Composable
fun SHistorialRoute(
    viewModel: SHistorialViewModel = koinInject()
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    SHistorialScreen(
        state = state,
        loadData = viewModel::loadData,
        onTabSelected = viewModel::switchTab
    )
}

@Composable
fun SHistorialScreen(
    state: SHistorialState,
    loadData: () -> Unit,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = { CoopTopBar(title = "Historial") },
        containerColor = CoopTheme.colorScheme.surface
    ) { padding ->
        if(state.isLoading){
            LoadingScreen(
                message = "Cargando historial..."
            )
        } else if(state.errorMessage != null){
            ErrorScreen(
                message = state.errorMessage!!,
                onRetry = loadData
            )
        } else {
            Column(
                modifier = modifier
                    .background(CoopTheme.colorScheme.surface)
                    .padding(padding)
                    .padding(vertical = 6.dp, horizontal = 24.dp)
            ){
                HistorialChipsRow(
                    selectedTabIndex = state.selectedTabIndex,
                    onTabSelected = onTabSelected,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                when(state.selectedTabIndex){
                    0 -> {
                        CoopOutlinedCard(
                            modifier = Modifier.padding(bottom = 18.dp)
                        ) {
                            CoopText(
                                text = "Total Aportes",
                                style = CoopTheme.typography.bodyLarge,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(horizontal = 16.dp).padding(top = 16.dp, bottom = 8.dp),
                            )

                            CoopText(
                                text = formatMoney(state.totalAportado),
                                style = CoopTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth().padding(bottom = 18.dp),
                                textAlign = TextAlign.Center
                            )
                        }

                        CoopOutlinedCard(
                            modifier = Modifier.padding(bottom = 12.dp)
                        ) {
                            CoopText(
                                text = "Capital por Pagar",
                                style = CoopTheme.typography.bodyLarge,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(horizontal = 16.dp).padding(top = 16.dp, bottom = 8.dp),
                            )

                            CoopText(
                                text = formatMoney(state.capitalPorPagar),
                                style = CoopTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth().padding(bottom = 18.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    1 -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(state.prestamos.size) { i ->
                                LoanItem(loan = state.prestamos[i])
                            }
                        }
                    }
                }
            }

        }

    }
}

@Composable
fun HistorialChipsRow(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    val chipOptions = listOf("Pagos", "Préstamos")

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        chipOptions.forEachIndexed { index, chip ->
            val isSelected = selectedTabIndex == index

            FilterChip(
                selected = isSelected,
                onClick = { onTabSelected(index) },
                label = {
                    CoopText(
                        text = chip,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = CoopTheme.colorScheme.surface,
                    labelColor = CoopTheme.colorScheme.onSurface,
                    iconColor = CoopTheme.colorScheme.onSurface,

                    selectedContainerColor = CoopTheme.colorScheme.primary,
                    selectedLabelColor = CoopTheme.colorScheme.onPrimary,
                    selectedLeadingIconColor = CoopTheme.colorScheme.onPrimary,
                    selectedTrailingIconColor = CoopTheme.colorScheme.onPrimary,

                    disabledContainerColor = CoopTheme.colorScheme.surface.copy(alpha = 0.12f),
                    disabledLabelColor = CoopTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                    disabledLeadingIconColor = CoopTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                    disabledTrailingIconColor = CoopTheme.colorScheme.onSurface.copy(alpha = 0.38f),

                    disabledSelectedContainerColor = CoopTheme.colorScheme.primary.copy(alpha = 0.12f)
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = if (isSelected)
                        CoopTheme.colorScheme.primary
                    else
                        CoopTheme.colorScheme.onSurface,
                    selected = isSelected,
                    enabled = true
                )

            )
        }
    }
}

@Composable
fun LoanItem(
    loan: Loan,
    modifier: Modifier = Modifier
) {
    CoopOutlinedCard(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Title: motivo
            CoopText(
                text = loan.reason,
                style = CoopTheme.typography.bodyLarge,
                fontWeight = FontWeight.ExtraBold,
                color = CoopTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Totales
            CoopText(
                text = "Total: ${formatMoney(loan.total)}",
                style = CoopTheme.typography.bodyMedium,
                color = CoopTheme.colorScheme.onSurface
            )
            CoopText(
                text = "Pagado: ${formatMoney(loan.payed)}",
                style = CoopTheme.typography.bodyMedium,
                color = CoopTheme.colorScheme.onSurface
            )
            CoopText(
                text = "Deuda: ${formatMoney(loan.debt)}",
                style = CoopTheme.typography.bodyMedium,
                color = CoopTheme.colorScheme.onSurface
            )
        }
    }
}
