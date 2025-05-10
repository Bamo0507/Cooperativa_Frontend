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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.cooperativa.data.localdb.SolicitudPrestamoMockData
import app.cooperativa.data.localdb.PrestamoMockData
import app.cooperativa.data.model.dto.Estados
import app.cooperativa.data.model.dto.Prestamo
import app.cooperativa.data.model.ui.BasicInfoLoan
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopOutlinedCard
import app.cooperativa.theme.components.CoopSearchBar
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar
import app.cooperativa.utils.PrestamoUtils

@Composable
fun PrestamosRoute() {
    val reqLoansState = rememberSaveable { mutableStateOf(SolicitudPrestamoMockData.getAllBasicInfo()) }
    val approvedLoansState = rememberSaveable { mutableStateOf(PrestamoMockData.getAllPrestamos()) }
    val selectedIndexState = rememberSaveable { mutableStateOf(0) }

    PrestamoScreen(
        reqLoans = reqLoansState.value,
        approvedLoans = approvedLoansState.value,
        selectedTabIndex = selectedIndexState.value,
        changeIndex = { selectedIndexState.value = it }
    )
}

@Composable
fun PrestamoScreen(
    reqLoans: List<BasicInfoLoan>,
    approvedLoans: List<Prestamo>,
    selectedTabIndex: Int,
    changeIndex: (Int) -> Unit = {},
    prestamoUtils: PrestamoUtils = PrestamoUtils,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { CoopTopBar(title = "Préstamos") },
        containerColor = CoopTheme.colorScheme.surface
    ) { padding ->
        Column(
            modifier = modifier
                .background(CoopTheme.colorScheme.surface)
                .padding(padding)
                .padding(vertical = 6.dp, horizontal = 12.dp)
        ) {
            FilterChipsRow(
                selectedIndex = selectedTabIndex,
                onSelect = changeIndex,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            when (selectedTabIndex) {
                0 -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(reqLoans) { basic ->
                            SolicitudItem(
                                idSolicitud = basic.id,
                                solicitudName = basic.loanName,
                                affiliatedName = basic.username,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                1 -> {
                    CoopSearchBar(
                        query = "",
                        onQueryChanged = {},
                        placeholder = "Buscar préstamo",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        //TODO: Incluir nombre del solicitante
                        items(approvedLoans) { prestamo ->
                            PrestamoVigenteItem(
                                prestamoName = prestamo.nombre,
                                montoTotal = prestamo.montoTotal,
                                cantCuotas = prestamo.mensualidadesPrestamo.count(),
                                cantPagadas = prestamoUtils.countPaidInstallments(prestamo),
                                montoCancelado = prestamoUtils.totalPaidAmount(prestamo),
                                montoPendiente = prestamoUtils.remainingAmount(prestamo),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                2 -> {
                    // TODO: Lista de préstamos completados, filtrar acorde
                    CoopSearchBar(
                        query = "",
                        onQueryChanged = {},
                        placeholder = "Buscar préstamo",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        //TODO: Incluir nombre del solicitante
                        items(approvedLoans) { prestamo ->
                            PrestamoCompletadoItem(
                                solicitanteName = "Bryan Martinez",
                                prestamoName = prestamo.nombre,
                                montoTotal = prestamo.montoTotal,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }


                }
            }
        }
    }
}

@Composable
fun SolicitudItem(
    idSolicitud: Int,
    solicitudName: String,
    affiliatedName: String,
    modifier: Modifier = Modifier
) {
    CoopOutlinedCard(
        onClick = { /* TODO */ },
        modifier = modifier.padding(vertical = 2.dp)
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
fun PrestamoCompletadoItem(
    prestamoName: String,
    solicitanteName: String,
    montoTotal: Float,
    modifier: Modifier = Modifier
){
    CoopOutlinedCard(
        modifier = modifier.padding(vertical = 2.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                CoopText(
                    text = prestamoName,
                    fontWeight = FontWeight.Bold,
                    style = CoopTheme.typography.bodyMedium,
                )

                Spacer(modifier = Modifier.height(4.dp))

                CoopText(
                    text = solicitanteName,
                    style = CoopTheme.typography.bodyMedium,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            CoopText(
                text = "Q${montoTotal.toString()}",
                style = CoopTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

//TODO: NOMBRE DE SOLICITANTE METERLO EN EL MODEL
@Composable
fun PrestamoVigenteItem(
    nombreSolicitante: String = "",
    prestamoName: String,
    montoTotal: Float,
    cantCuotas: Int,
    cantPagadas: Int,
    montoCancelado: Float,
    montoPendiente: Float,
    modifier: Modifier = Modifier
) {
    CoopOutlinedCard(
        modifier = modifier.padding(vertical = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            CoopText(
                text = "$prestamoName",
                fontWeight = FontWeight.Bold,
                color = CoopTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            //Aqui tengo que hacer el cambio
            CoopText(
                text = "Bryan Martinez",
                style = CoopTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = CoopTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            CoopText(
                text = "Cuotas: $cantPagadas / $cantCuotas",
                style = CoopTheme.typography.bodyMedium,
                color = CoopTheme.colorScheme.onSurface
            )

            CoopText(
                text = "Pagado: $montoCancelado",
                style = CoopTheme.typography.bodyMedium,
                color = CoopTheme.colorScheme.onSurface
            )

            CoopText(
                text = "Pendiente: $montoPendiente",
                style = CoopTheme.typography.bodyMedium,
                color = CoopTheme.colorScheme.onSurface
            )

            CoopText(
                text = "Total: $montoTotal",
                style = CoopTheme.typography.bodyMedium,
                color = CoopTheme.colorScheme.onSurface
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
