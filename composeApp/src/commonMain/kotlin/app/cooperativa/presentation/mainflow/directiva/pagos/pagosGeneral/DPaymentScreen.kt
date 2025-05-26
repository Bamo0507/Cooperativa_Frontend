package app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.koinInject

import app.cooperativa.data.model.dto.Fine
import app.cooperativa.data.model.dto.FineType
import app.cooperativa.data.model.ui.BasicInfoPayment
import app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral.DPaymentsState
import app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral.DPaymentsViewModel
import app.cooperativa.presentation.utils.ErrorScreen
import app.cooperativa.presentation.utils.LoadingScreen
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopOutlinedCard
import app.cooperativa.theme.components.CoopSearchBar
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar
import app.cooperativa.theme.utils.dateToString
import app.cooperativa.utils.formatMoney

/**
 * Route: inyecta ViewModel y observa el estado para la pantalla de Pagos.
 */
@Composable
fun DPaymentsRoute(
    onPendingPaymentClick: (Int) -> Unit,
    onPaidPaymentClick: (Int) -> Unit,
    viewModel: DPaymentsViewModel = koinInject()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    DPaymentsScreen(
        state = state,
        onTabSelected = viewModel::onTabSelected,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onPendingPaymentClick = onPendingPaymentClick,
        onPaidPaymentClick = onPaidPaymentClick,
        loadData = viewModel::loadData
    )
}

/**
 * Screen: recibe el estado completo y lambdas de interacción.
 */
@Composable
fun DPaymentsScreen(
    state: DPaymentsState,
    onTabSelected: (Int) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onPendingPaymentClick: (Int) -> Unit,
    onPaidPaymentClick: (Int) -> Unit,
    loadData: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { CoopTopBar(title = "Pagos") },
        containerColor = CoopTheme.colorScheme.surface
    ) { padding ->
        if (state.isLoading) {
            LoadingScreen(
                message = "Cargando pagos"
            )
        } else if (state.errorMessage != null) {
            ErrorScreen(
                message = state.errorMessage,
                onRetry = loadData
            )
        } else {
            Column(
                modifier = modifier
                    .background(CoopTheme.colorScheme.surface)
                    .padding(padding)
                    .padding(vertical = 6.dp, horizontal = 24.dp)
            ) {
                // Chips para filtrar pestañas
                FilterChipsRow(
                    selectedIndex = state.selectedTabIndex,
                    onSelect = onTabSelected,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                when (state.selectedTabIndex) {
                    0 -> {
                        // Pagos pendientes
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.pendingPayments) { basic ->
                                PaymentItem(
                                    idPayment = basic.id,
                                    paymentName = basic.paymentName,
                                    affiliatedName = basic.username,
                                    onPaymentClick = onPendingPaymentClick,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp)
                                )
                            }
                        }
                    }
                    1 -> {
                        CoopSearchBar(
                            query = state.searchQuery,
                            onQueryChanged = onSearchQueryChange,
                            placeholder = "Buscar...",
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                        )
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.paidPayments) { basic ->
                                PaymentItem(
                                    idPayment = basic.id,
                                    paymentName = basic.paymentName,
                                    affiliatedName = basic.username,
                                    onPaymentClick = onPaidPaymentClick,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp)
                                )
                            }
                        }
                    }
                    2 -> {
                        CoopSearchBar(
                            query = state.searchQuery,
                            onQueryChanged = onSearchQueryChange,
                            placeholder = "Buscar...",
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                        )
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.fines) { fine ->
                                FineSection(
                                    fine = fine,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
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
fun PaymentItem(
    idPayment: Int,
    paymentName: String,
    affiliatedName: String,
    onPaymentClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    CoopOutlinedCard(
        onClick = { onPaymentClick(idPayment) },
        modifier = modifier.padding(vertical = 2.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                CoopText(
                    text = paymentName,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                CoopText(text = affiliatedName)
            }
            Spacer(modifier = Modifier.width(8.dp))
            CoopIcon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Ir al detalle",
                tint = CoopTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun FilterChipsRow(
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val chipOptions = listOf("Pendientes", "Pagados", "Moras")

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        chipOptions.forEachIndexed { index, chip ->
            val isSelected = selectedIndex == index

            FilterChip(
                selected = isSelected,
                onClick = { onSelect(index) },
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
fun FineSection(
    fine: Fine,
    modifier: Modifier = Modifier
){
    val hasQuotaFines = fine.fineDetails.any { it.type == FineType.QUOTA }
    val hasLoanFines = fine.fineDetails.any { it.type == FineType.LOAN }

    Column(modifier = modifier.fillMaxWidth()) {
        CoopText(
            text = fine.userName,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            style = CoopTheme.typography.bodyLarge,
            color = CoopTheme.colorScheme.onSecondary
        )

        if (hasQuotaFines) {
            CoopOutlinedCard(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    CoopText(
                        text = "Cuotas",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        style = CoopTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    fine.fineDetails
                        .filter { it.type == FineType.QUOTA }
                        .forEach { fineDetail ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CoopText(
                                    text = dateToString(fineDetail.date),
                                    style = CoopTheme.typography.bodyMedium
                                )

                                CoopText(
                                    text = formatMoney(fineDetail.amount),
                                    textAlign = TextAlign.End,
                                    style = CoopTheme.typography.bodyMedium
                                )
                            }
                        }
                }
            }
        }

        if(hasLoanFines){
            CoopOutlinedCard(
                onClick = { /* TODO */ },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ){
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    CoopText(
                        text = "Préstamos",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        style = CoopTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    fine.fineDetails.filter { it.type == FineType.LOAN }.forEach { fineDetail ->
                        Row (modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ){
                            Column {
                                CoopText(
                                    text = dateToString(fineDetail.date),
                                    textAlign = TextAlign.Start,
                                    style = CoopTheme.typography.bodyMedium
                                )
                                CoopText(
                                    text = fineDetail.name,
                                    textAlign = TextAlign.Start,
                                    style = CoopTheme.typography.bodyMedium
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))
                            CoopText(
                                text = formatMoney(fineDetail.amount),
                                textAlign = TextAlign.End,
                                style = CoopTheme.typography.bodyMedium
                            )
                        }
                    }

                }
            }
        }

    }
}
