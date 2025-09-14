package app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.IconButton
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.koinInject
import app.cooperativa.data.model.dto.Fine
import app.cooperativa.data.model.dto.FineType
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
import org.jetbrains.compose.resources.painterResource
import cooperativa.composeapp.generated.resources.Res
import cooperativa.composeapp.generated.resources.ic_no_results

/**
 * Route: inyecta ViewModel y observa el estado para la pantalla de Pagos.
 */
@Composable
fun DPaymentsRoute(
    onPendingPaymentClick: (Int) -> Unit,
    onPaidPaymentClick: (Int) -> Unit,
    onFineClick: (Int) -> Unit,
    viewModel: DPaymentsViewModel = koinInject()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    DPaymentsScreen(
        state = state,
        onTabSelected = viewModel::onTabSelected,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onPendingPaymentClick = onPendingPaymentClick,
        onFineClick = onFineClick,
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
    onFineClick: (Int) -> Unit,
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
                        CoopSearchBar(
                            query = state.searchQuery,
                            onQueryChanged = onSearchQueryChange,
                            placeholder = "Buscar...",
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                        )
                        if (state.pendingPayments.isEmpty()) {
                            NoResultsView()
                        } else {
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
                                        dateOfPayment = basic.dateOfPayment,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 2.dp)
                                    )
                                }
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
                        if (state.paidPayments.isEmpty()) {
                            NoResultsView()
                        } else {
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
                                        dateOfPayment = basic.dateOfPayment,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 2.dp)
                                    )
                                }
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
                        if (state.fines.isEmpty()) {
                            NoResultsView()
                        } else {
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
                                            .padding(vertical = 4.dp),
                                        onFineClick = onFineClick
                                    )
                                }
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
    dateOfPayment: String,
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
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                CoopText(
                    text = paymentName,
                    fontWeight = FontWeight.Bold,
                    style = CoopTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                CoopText(
                    text = affiliatedName,
                    style = CoopTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                CoopText(
                    text = dateOfPayment,
                    style = CoopTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier.size(24.dp),
                contentAlignment = Alignment.Center
            ) {
                CoopIcon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Ir al detalle",
                    tint = CoopTheme.colorScheme.secondary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun FilterChipsRow(
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val chipOptions = listOf("Pendientes", "Pagados", "Multas")

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
    onFineClick: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    val hasQuotaFines = fine.fineDetails.any { it.type == FineType.QUOTA }
    val hasLoanFines = fine.fineDetails.any { it.type == FineType.LOAN }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CoopText(
                text = fine.userName,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                style = CoopTheme.typography.bodyLarge,
                color = CoopTheme.colorScheme.onSecondary,
            )
            Box(
                contentAlignment = Alignment.CenterEnd
            ){
                IconButton(onClick = { onFineClick(fine.userId) }) {
                    CoopIcon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Editar mora",
                        tint = CoopTheme.colorScheme.tertiary,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

            }
        }

        if (hasQuotaFines) {
            CoopOutlinedCard(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
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

@Composable
private fun NoResultsView() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_no_results),
            contentDescription = "Sin resultados"
        )
        CoopText(
            text = "No hay resultados",
            fontWeight = FontWeight.Bold,
            style = CoopTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 6.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}