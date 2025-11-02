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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.IconButton
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Surface
import androidx.compose.ui.window.Dialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
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
import app.cooperativa.presentation.utils.ConfettiOverlay
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopOutlinedCard
import app.cooperativa.theme.components.CoopOutlinedTextField
import app.cooperativa.theme.components.CoopSearchBar
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar
import app.cooperativa.theme.components.CoopButton
import app.cooperativa.theme.components.CoopOutlinedButton
import app.cooperativa.theme.utils.dateToString
import app.cooperativa.utils.formatMoney
import org.jetbrains.compose.resources.painterResource
import cooperativa.composeapp.generated.resources.Res
import cooperativa.composeapp.generated.resources.ic_no_results
import cooperativa.composeapp.generated.resources.ic_fines

/**
 * Route: inyecta ViewModel y observa el estado para la pantalla de Pagos.
 */
@Composable
fun DPaymentsRoute(
    onPendingPaymentClick: (String) -> Unit,
    onPaidPaymentClick: (String) -> Unit,
    viewModel: DPaymentsViewModel = koinInject()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    DPaymentsScreen(
        state = state,
        onTabSelected = viewModel::onTabSelected,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onPendingPaymentClick = onPendingPaymentClick,
        onPaidPaymentClick = onPaidPaymentClick,
        onFineEditClick = viewModel::onFineEditClick,
        onAmountChange = viewModel::onAmountChanged,
        onDeleteFine = viewModel::onDeleteFine,
        onUpdateFine = viewModel::onUpdateFine,
        onCloseEditDialog = viewModel::onCloseEditDialog,
        onConfettiFinished = viewModel::onConfettiFinished,
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
    onPendingPaymentClick: (String) -> Unit,
    onPaidPaymentClick: (String) -> Unit,
    onFineEditClick: (fineId: String, userId: String, reason: String, amount: Float) -> Unit,
    onAmountChange: (String) -> Unit,
    onDeleteFine: () -> Unit,
    onUpdateFine: () -> Unit,
    onCloseEditDialog: () -> Unit,
    onConfettiFinished: () -> Unit,
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
            Box(
                modifier = modifier
                    .background(CoopTheme.colorScheme.surface)
                    .padding(padding)
                    .padding(horizontal = 24.dp)
                    .padding(top = 6.dp)
            ) {
                // MAIN CONTENT
                Column(
                    modifier = Modifier.fillMaxSize()
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
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            if (state.pendingPayments.isEmpty()) {
                                NoResultsView()
                            } else {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    contentPadding = PaddingValues(top = 8.dp),
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
                                    item { Spacer(modifier = Modifier.height(6.dp)) }
                                }
                            }
                        }
                        1 -> {
                            CoopSearchBar(
                                query = state.searchQuery,
                                onQueryChanged = onSearchQueryChange,
                                placeholder = "Buscar...",
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            if (state.paidPayments.isEmpty()) {
                                NoResultsView()
                            } else {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    contentPadding = PaddingValues(top = 8.dp),
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
                                    item { Spacer(modifier = Modifier.height(6.dp)) }
                                }
                            }
                        }
                        2 -> {
                            CoopSearchBar(
                                query = state.searchQuery,
                                onQueryChanged = onSearchQueryChange,
                                placeholder = "Buscar...",
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            if (state.fines.isEmpty()) {
                                NoResultsView()
                            } else {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    contentPadding = PaddingValues(top = 8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(state.fines) { fine ->
                                        FineSimplifiedCard(
                                            fine = fine,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            onEditFineClick = { fineId, userId, reason, amount ->
                                                onFineEditClick(fineId, userId, reason, amount)
                                            }
                                        )
                                    }
                                    item { Spacer(modifier = Modifier.height(6.dp)) }
                                }
                            }
                        }
                    }
                }

                EditFineDialog(
                    visible = state.isEditDialogVisible,
                    reason = state.editReason,
                    amountText = state.editAmountText,
                    isSubmitting = state.isSubmittingEdit,
                    errorMessage = state.editErrorMessage,
                    onAmountChange = onAmountChange,
                    onDeleteClick = onDeleteFine,
                    onUpdateClick = onUpdateFine,
                    onDismiss = onCloseEditDialog
                )

                ConfettiOverlay(
                    visible = state.showConfetti,
                    onFinished = onConfettiFinished,
                    modifier = Modifier.matchParentSize()
                )
            }
        }
    }
}


@Composable
fun PaymentItem(
    idPayment: String,
    paymentName: String,
    affiliatedName: String,
    dateOfPayment: String,
    onPaymentClick: (String) -> Unit,
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
                    color = CoopTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                CoopText(
                    text = affiliatedName,
                    style = CoopTheme.typography.bodyMedium,
                    color = CoopTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                CoopText(
                    text = dateOfPayment,
                    style = CoopTheme.typography.bodySmall,
                    color = CoopTheme.colorScheme.onSurface,
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
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) CoopTheme.colorScheme.secondary else CoopTheme.colorScheme.onSecondary,
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
fun FineSimplifiedCard(
    fine: Fine,
    onEditFineClick: (fineId: String, userId: String, reason: String, amount: Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Encabezado solo con el nombre del usuario (ya sin lapiz aquí)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CoopText(
                text = fine.userName,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                style = CoopTheme.typography.bodyLarge,
                color = CoopTheme.colorScheme.onSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
        }

        if (fine.fineDetails.isNotEmpty()) {
            CoopOutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, top = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    fine.fineDetails.forEach { detail ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Reason
                            CoopText(
                                text = detail.name,
                                style = CoopTheme.typography.bodyMedium,
                                color = CoopTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f)
                            )
                            // Amount
                            CoopText(
                                text = formatMoney(detail.amount),
                                textAlign = TextAlign.End,
                                style = CoopTheme.typography.bodyMedium,
                                color = CoopTheme.colorScheme.onSecondary,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            // Edit icon (a la derecha)
                            IconButton(
                                onClick = {
                                    onEditFineClick(
                                        detail.id,
                                        fine.userId,
                                        detail.name,
                                        detail.amount
                                    )
                                },
                                modifier = Modifier.size(40.dp)
                            ) {
                                CoopIcon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "Editar multa",
                                    tint = CoopTheme.colorScheme.tertiary,
                                    modifier = Modifier.size(22.dp)
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
private fun EditFineDialog(
    visible: Boolean,
    reason: String,
    amountText: String,
    isSubmitting: Boolean,
    errorMessage: String?,
    onAmountChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onUpdateClick: () -> Unit,
    onDismiss: () -> Unit
) {
    if (!visible) return

    Dialog(onDismissRequest = { if (!isSubmitting) onDismiss() }) {
        Surface(
            shape = androidx.compose.material3.MaterialTheme.shapes.medium,
            color = CoopTheme.colorScheme.surface,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(min = 320.dp)
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),   // (fix del typo)
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_fines),
                    contentDescription = null,
                    modifier = Modifier.size(88.dp)
                )

                CoopText(
                    text = "Editar multa",
                    style = CoopTheme.typography.titleMedium,
                    color = CoopTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                CoopText(
                    text = "Actualiza el monto o elimina la multa.",
                    style = CoopTheme.typography.bodyMedium,
                    color = CoopTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                CoopOutlinedTextField(
                    value = amountText,
                    onValueChange = onAmountChange,
                    placeholder = {
                        CoopText(
                            text = "Monto",
                            style = CoopTheme.typography.bodyMedium
                        )
                    },
                    singleLine = true,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = CoopTheme.typography.bodyMedium,
                    focusedBorderColor = CoopTheme.colorScheme.primary,
                    unfocusedBorderColor = CoopTheme.colorScheme.primary.copy(alpha = 0.5f),
                    cursorColor = CoopTheme.colorScheme.primary
                )

                if (errorMessage != null) {
                    CoopText(
                        text = errorMessage,
                        style = CoopTheme.typography.bodySmall,
                        color = CoopTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(Modifier.height(4.dp))

                // Acciones: misma fila, mismo ancho, sin iconos
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Eliminar (rojo)
                    CoopButton(
                        onClick = onDeleteClick,
                        enabled = !isSubmitting,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CoopTheme.colorScheme.rejected.copy(0.7f),
                            contentColor = CoopTheme.colorScheme.onSecondary,
                            disabledContainerColor = CoopTheme.colorScheme.rejected.copy(alpha = 0.60f),
                            disabledContentColor = CoopTheme.colorScheme.onPrimary.copy(alpha = 0.65f)
                        )
                    ) {
                        CoopText(
                            text = "Eliminar",
                            color = CoopTheme.colorScheme.onPrimary,
                            style = CoopTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Actualizar (primario)
                    CoopButton(
                        onClick = onUpdateClick,
                        enabled = !isSubmitting,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CoopTheme.colorScheme.primary,
                            contentColor = CoopTheme.colorScheme.onSecondary,
                            disabledContainerColor = CoopTheme.colorScheme.primary.copy(alpha = 0.60f),
                            disabledContentColor = CoopTheme.colorScheme.onPrimary.copy(alpha = 0.65f)
                        )
                    ) {
                        CoopText(
                            text = "Actualizar",
                            color = CoopTheme.colorScheme.onPrimary,
                            style = CoopTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
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
            contentDescription = "Sin resultados",
            modifier = Modifier.size(128.dp)
        )
        CoopText(
            text = "No hay resultados",
            fontWeight = FontWeight.Bold,
            style = CoopTheme.typography.titleMedium,
            color = CoopTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .offset(y = (-8).dp)
                .padding(vertical = 6.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}