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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.cooperativa.data.localdb.FineMockData
import app.cooperativa.data.localdb.PaymentMockData
import app.cooperativa.data.model.dto.Fine
import app.cooperativa.data.model.dto.FineType
import app.cooperativa.data.model.ui.BasicInfoPayment
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopOutlinedCard
import app.cooperativa.theme.components.CoopSearchBar
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar
import app.cooperativa.theme.utils.dateToString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DPaymentsRoute(
    onPaymentClick: (Int) -> Unit
) {
    val payments = rememberSaveable { mutableStateOf(PaymentMockData.getAllPaymentsBasicInfo()) }
    val fines = rememberSaveable { mutableStateOf(FineMockData.getAllFines()) }

    val selectedTabIndex = rememberSaveable { mutableStateOf(0) }

    DPaymentsScreen(
        payments = payments.value,
        fines = fines.value,
        selectedTabIndex = selectedTabIndex.value,
        changeIndex = { selectedTabIndex.value = it },
        onPaymentClick = onPaymentClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DPaymentsScreen(
    fines: List<Fine>,
    payments: List<BasicInfoPayment>,
    selectedTabIndex: Int,
    changeIndex: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    onPaymentClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            CoopTopBar(title = "Pagos")
        },
        containerColor = CoopTheme.colorScheme.surface,
    ) { padding ->
        Column(
            modifier = modifier
                .background(CoopTheme.colorScheme.surface)
                .padding(padding)
                .padding(vertical = 6.dp, horizontal = 24.dp)
        ) {
            // Chips
            FilterChipsRow(
                selectedIndex = selectedTabIndex,
                onSelect = changeIndex,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Filtrar y mostrar según estado
            if (selectedTabIndex == 0) {
                // Pendientes
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(payments.filter { it.isPaymentPending }) { basic ->
                        PaymentItem(
                            idPayment = basic.id,
                            paymentName = basic.paymentName,
                            affiliatedName = basic.username,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            onPaymentClick = onPaymentClick
                        )
                    }
                }
            } else if (selectedTabIndex == 1) {
                // Pagados
                // Search bar
                CoopSearchBar(
                    query = "",
                    onQueryChanged = {},
                    placeholder = "Bryan Martinez",
                    modifier = Modifier.padding(bottom = 8.dp).padding(horizontal = 4.dp)
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(payments.filter { !it.isPaymentPending }) { basic ->
                        PaymentItem(
                            idPayment = basic.id,
                            paymentName = basic.paymentName,
                            affiliatedName = basic.username,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 2.dp),
                            onPaymentClick = { /*TODO*/ }
                        )
                    }
                }

            } else {
                // En mora
                CoopSearchBar(
                    query = "",
                    onQueryChanged = {},
                    placeholder = "Bryan Martinez",
                    modifier = Modifier.padding(bottom = 8.dp).padding(horizontal = 4.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(fines) { fine ->
                        FineSection(
                            fine = fine,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
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
                                    text = "Q${(fineDetail.amount)}",
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
                                text = "Q${fineDetail.amount.toString()}",
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
