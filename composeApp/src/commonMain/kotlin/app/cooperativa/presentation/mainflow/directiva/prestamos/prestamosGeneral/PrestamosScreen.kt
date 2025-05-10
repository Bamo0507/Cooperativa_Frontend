package app.cooperativa.presentation.mainflow.directiva.prestamos.prestamosGeneral

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import app.cooperativa.data.localdb.SolicitudPrestamoMockData
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.cooperativa.data.localdb.PaymentMockData
import app.cooperativa.data.localdb.PrestamoMockData
import app.cooperativa.data.model.dto.Prestamo
import app.cooperativa.data.model.dto.SolicitudPrestamo
import app.cooperativa.data.model.ui.BasicInfoLoan
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopOutlinedCard
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar

@Composable
fun PrestamosRoute() {
    val reqLoans = rememberSaveable { mutableStateOf(SolicitudPrestamoMockData.getAllBasicInfo()) }
    val approvedLoans = rememberSaveable { mutableStateOf(PrestamoMockData.getAllPrestamos())}

    PrestamoScreen(
        reqLoans = reqLoans.value,
        approvedLoans = approvedLoans.value,
        selectedTabIndex = 0
    )
}

@Composable
fun PrestamoScreen(
    reqLoans: List<BasicInfoLoan>,
    approvedLoans: List<Prestamo>,
    selectedTabIndex: Int,
    changeIndex: (Int) -> Unit = {},
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = {
            CoopTopBar(title = "Préstamos")
        },
        containerColor = CoopTheme.colorScheme.surface,
    ){ padding ->
        Column(
            modifier = modifier
                .background(CoopTheme.colorScheme.surface)
                .padding(padding)
                .padding(vertical = 6.dp, horizontal = 12.dp)
        ) {
            //Chips
            FilterChipsRow(
                selectedIndex = selectedTabIndex,
                onSelect = { changeIndex(it) },
                modifier = Modifier.padding(bottom = 12.dp)
            )

            if(selectedTabIndex == 0) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(reqLoans.size) { index ->
                        SoliticudItem(
                            idSolicitud = reqLoans[index].id,
                            solicitudName = reqLoans[index].loanName,
                            affiliatedName = reqLoans[index].username,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 4.dp,
                                    vertical = 2.dp
                                )
                        )
                    }
                }
            } else if (selectedTabIndex == 1) {
                //TODO
            } else if (selectedTabIndex == 2 ) {
                //TODO
            }

        }
    }
}

@Composable
fun SoliticudItem(
    idSolicitud: Int,
    solicitudName: String,
    affiliatedName: String,
    modifier: Modifier = Modifier
){
    CoopOutlinedCard(
        onClick = { /* TODO */ },
        modifier = modifier.padding(vertical = 2.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column {
                CoopText(
                    text = solicitudName,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                CoopText(
                    text = affiliatedName
                )

            }

            Spacer(modifier = Modifier.width(8.dp))

            CoopIcon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Ir al detalle",
                tint = CoopTheme.colorScheme.primary
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
    val chipOptions = listOf("Solicitud", "Vigentes", "Completados")

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
