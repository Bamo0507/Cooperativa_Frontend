package app.cooperativa.presentation.mainflow.socios.prestamos.mainPrestamos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cooperativa.data.model.dto.Estados
import app.cooperativa.presentation.utils.ErrorScreen
import app.cooperativa.presentation.utils.LoadingScreen
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopIconButton
import app.cooperativa.theme.components.CoopOutlinedCard
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar
import org.koin.compose.koinInject

@Composable
fun SPrestamoRoute(
    onLoadPagareClick: () -> Unit,
    onSolicitudClick: () -> Unit,
    viewModel: SPrestamoViewModel = koinInject()
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    SPrestamoScreen(
        state = state,
        loadData = viewModel::loadData,
        onTabSelected = viewModel::onTabSelected,
        onLoadPagareClick = onLoadPagareClick,
        onSolicitudClick = onSolicitudClick
    )
}

@Composable
fun SPrestamoScreen(
    state: SPrestamoState,
    onLoadPagareClick: () -> Unit,
    onSolicitudClick: () -> Unit,
    loadData: () -> Unit,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = { CoopTopBar(title = "Préstamos") },
        containerColor = CoopTheme.colorScheme.surface,
        floatingActionButton = {
            if (!state.isLoading){
                if (state.selectedTabIndex == 0) {
                    FloatingActionButton(
                        containerColor = CoopTheme.colorScheme.secondary,
                        contentColor = CoopTheme.colorScheme.onSecondary,
                        onClick = onSolicitudClick, //TODO: Agregar solicitud
                        content = {
                            CoopIcon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Solicitar préstamo"
                            )
                        }
                    )
                } else {
                    FloatingActionButton(
                        containerColor = CoopTheme.colorScheme.secondary,
                        contentColor = CoopTheme.colorScheme.onSecondary,
                        onClick = onLoadPagareClick, //TODO: Cargar pagare
                        content = {
                            CoopIcon(
                                imageVector = Icons.Filled.AttachFile,
                                contentDescription = "Cargar pagare"
                            )
                        }
                    )
                }

            }
        }
    ){ padding ->
        if(state.isLoading){
            LoadingScreen(
                message = "Cargando préstamos..."
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
                StatusChipsRow(
                    selectedTabIndex = state.selectedTabIndex,
                    onTabSelected = onTabSelected,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                when(state.selectedTabIndex){
                    0 -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ){
                            items(state.prestamos.size){ idx ->
                                PrestamoStatusCard(
                                    nombrePrestamo = state.prestamos[idx].prestamoNombre,
                                    estado = state.prestamos[idx].estadoPrestamo,
                                    linkDescargaPagare = state.prestamos[idx].linkDescargaPagare
                                )
                            }
                        }

                    }
                    1 -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(state.pagares.size){ idx ->
                                PagareStatusCard(
                                    nombrePrestamo = state.pagares[idx].prestamoNombre,
                                    estado = state.pagares[idx].estadoPagare,
                                    navToPagareSelection = onLoadPagareClick
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
fun PrestamoStatusCard(
    nombrePrestamo: String,
    estado: Estados,
    linkDescargaPagare: String,
    modifier: Modifier = Modifier
){
    CoopOutlinedCard(
        modifier = modifier
    ) {
        Row (
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceAround
            ){
                CoopText(
                    text = nombrePrestamo,
                    style = CoopTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = CoopTheme.colorScheme.onSurface
                )

                CoopText(
                    text = getPrestamoStatusText(estado),
                    style = CoopTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = getStatusColor(estado)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (estado == Estados.APROBADO){
                CoopIconButton(
                    onClick = {}, //TODO: Agregar Descarga
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Transparent,
                    )
                ){
                    CoopIcon(
                        imageVector = Icons.Filled.Download,
                        contentDescription = "Descargar pagare"
                    )
                }

            }
        }

    }
}

@Composable
fun PagareStatusCard(
    nombrePrestamo: String,
    estado: Estados,
    navToPagareSelection: () -> Unit,
    modifier: Modifier = Modifier
){
    var colorText = getStatusColor(estado)

    CoopOutlinedCard(
        modifier = modifier
    ) {
        Row (
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceAround
            ){
                CoopText(
                    text = nombrePrestamo,
                    style = CoopTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = CoopTheme.colorScheme.onSurface
                )

                CoopText(
                    text = getPagareStatusText(estado),
                    style = CoopTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = colorText
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (estado == Estados.RECHAZADO){
                CoopIconButton(
                    onClick = navToPagareSelection,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Transparent,
                    )
                ){
                    CoopIcon(
                        Icons.Default.Refresh,
                        contentDescription = "Cargar Pagare",
                    )
                }
            }

        }
    }

}

fun getPrestamoStatusText(estado: Estados): String{
    return when(estado){
        Estados.APROBADO -> "Aprobado"
        Estados.PENDIENTE -> "En Revisión"
        Estados.RECHAZADO -> "Rechazado"
    }
}

fun getPagareStatusText(estado: Estados): String{
    return when(estado){
        Estados.APROBADO -> "Aprobado"
        Estados.PENDIENTE -> "En Revisión"
        Estados.RECHAZADO -> "Volver a Cargar"
    }
}

@Composable
fun getStatusColor(estado: Estados): Color{
    return when(estado){
        Estados.APROBADO -> CoopTheme.colorScheme.approved
        Estados.PENDIENTE -> CoopTheme.colorScheme.pending
        Estados.RECHAZADO -> CoopTheme.colorScheme.rejected
    }
}

@Composable
fun StatusChipsRow(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    val chipOptions = listOf("Préstamos", "Pagarés")

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