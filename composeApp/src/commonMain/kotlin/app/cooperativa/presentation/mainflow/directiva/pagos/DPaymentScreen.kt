package app.cooperativa.presentation.mainflow.directiva.pagos

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.cooperativa.data.localdb.PaymentMockData
import app.cooperativa.data.model.dto.Payment
import app.cooperativa.data.model.ui.BasicInfoPayment
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopSearchBar
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DPaymentsRoute() {
    val payments = rememberSaveable { mutableStateOf(PaymentMockData.getAllPaymentsBasicInfo()) }

    DPaymentsScreen(
        payments = payments.value,
        selectedTabIndex = 0
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DPaymentsScreen(
    payments: List<BasicInfoPayment>,
    selectedTabIndex: Int,
    changeIndex: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val chipOptions = listOf("Pendientes", "Pagados", "Moras")

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
                .padding(vertical = 6.dp, horizontal = 8.dp)
        ) {
            // Chips
            FilterChipsRow(
                selectedIndex = selectedTabIndex,
                onSelect = {},
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Filtrar y mostrar según estado
            if (selectedTabIndex == 0) {
                // Pendientes
                payments.forEach { basic ->
                    if(basic.isPaymentPending == true){
                        PaymentItem(
                            idPayment = basic.id,
                            paymentName = basic.paymentName,
                            affiliatedName = basic.username,
                            modifier = Modifier.padding(vertical = 8.dp)
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
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                payments.forEach { basic ->
                    if(basic.isPaymentPending == false){
                        PaymentItem(
                            idPayment = basic.id,
                            paymentName = basic.paymentName,
                            affiliatedName = basic.username,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }

            } else {
                // En mora

            }
        }
    }
}

@Composable
fun PaymentItem(
    idPayment: Int,
    paymentName: String,
    affiliatedName: String,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        onClick = { /* TODO */ },
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = CoopTheme.colorScheme.surface,
            contentColor = CoopTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = 4.dp
        )

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
