package app.cooperativa.presentation.mainflow.socios.pagos.pagoStatus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.cooperativa.data.model.dto.Estados
import app.cooperativa.data.model.dto.PagosStatus
import app.cooperativa.presentation.utils.getStatusColor
import app.cooperativa.presentation.utils.getStatusText
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopOutlinedCard
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar

@Composable
fun SPagosStatusRoute(

){

    SPagosStatusScreen()
}

@Composable
fun SPagosStatusScreen(
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = {
            CoopTopBar(title="Pagos")
        },
        containerColor = CoopTheme.colorScheme.surface,
        floatingActionButton = {
            //TODO: Wrap in if(!sate.isLoading) when VM is managed
            FloatingActionButton(
                containerColor = CoopTheme.colorScheme.secondary,
                contentColor = CoopTheme.colorScheme.onSecondary,
                onClick = { /*TODO*/ },
                content = {
                    CoopIcon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Presentar Pago"
                    )
                }
            )
        }
    ){ padding ->
        val payments = listOf(
            PagosStatus(
                pagoId = 1,
                nombrePago = "Pago 1",
                estado = Estados.PENDIENTE
            ),
            PagosStatus(
                pagoId = 2,
                nombrePago = "Pago 2",
                estado = Estados.APROBADO
            ),
            PagosStatus(
                pagoId = 3,
                nombrePago = "Pago 3",
                estado = Estados.RECHAZADO
            )
        )
        // Manage condicionts for error fetching or loading screen
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(CoopTheme.colorScheme.surface)
                .padding(padding)
                .padding(vertical = 6.dp, horizontal = 24.dp)
                .padding(top=14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // replace with state
            items(payments.size){idx ->
                PagoStatusCard(
                    nombrePago = payments[idx].nombrePago,
                    estado = payments[idx].estado
                )
            }
        }
    }

}

@Composable
fun PagoStatusCard(
    nombrePago: String,
    estado: Estados,
    modifier: Modifier = Modifier
){
    var colorText = getStatusColor(estado)

    CoopOutlinedCard(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                verticalArrangement = Arrangement.SpaceAround
            ){
                CoopText(
                    text = nombrePago,
                    style = CoopTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = CoopTheme.colorScheme.onSurface
                )

                CoopText(
                    text = getStatusText(estado),
                    style = CoopTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = colorText
                )
            }
        }

    }
}