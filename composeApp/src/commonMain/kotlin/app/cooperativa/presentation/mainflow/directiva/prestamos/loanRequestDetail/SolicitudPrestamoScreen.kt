package app.cooperativa.presentation.mainflow.directiva.prestamos.loanRequestDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.cooperativa.data.localdb.SolicitudPrestamoMockData
import app.cooperativa.data.model.dto.SolicitudPrestamo
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopButton
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopOutlinedButton
import app.cooperativa.theme.components.CoopOutlinedCard
import app.cooperativa.theme.components.CoopOutlinedTextField
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar

@Composable
fun SolicitudPrestamoRoute(
    solicitudId: Int,
    onBackClick: () -> Unit
){
    val prestamoSolicitud = SolicitudPrestamoMockData.getSolicitudById(solicitudId)

    if (prestamoSolicitud != null) {
        SolicitudPrestamoScreen(
            prestamo = prestamoSolicitud,
            onBackClick = { onBackClick() }
        )
    }
}

@Composable
fun SolicitudPrestamoScreen(
    prestamo: SolicitudPrestamo,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = {
            CoopTopBar(
                title = prestamo.nombrePrestamo,
                leadingArrow = true,
                onBackClick = onBackClick,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        },
        containerColor = CoopTheme.colorScheme.surface
    ) { padding ->
        Column(
            modifier = modifier
                .background(CoopTheme.colorScheme.surface)
                .padding(padding)
                .padding(vertical = 6.dp, horizontal = 24.dp)
                .verticalScroll(state = rememberScrollState(), enabled = true)
        ){
            Spacer(modifier = Modifier.height(8.dp))

            CoopText(
                text = prestamo.nombreSolicitante,
                fontWeight = FontWeight.Bold,
                style = CoopTheme.typography.bodyLarge,
                color = CoopTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            CoopOutlinedCard(
                containerColor = CoopTheme.colorScheme.primary,
                elevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp)
                ) {
                    CoopText(
                        text = "Monto",
                        fontWeight = FontWeight.Bold,
                        color = CoopTheme.colorScheme.onSurface,
                        style = CoopTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    CoopText(
                        text = "Q${prestamo.montoTotal.toString()}",
                        color = CoopTheme.colorScheme.onSurface,
                        style = CoopTheme.typography.bodyMedium
                    )

                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CoopOutlinedCard(
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp)
                ) {
                    CoopText(
                        text = "Motivo",
                        fontWeight = FontWeight.Bold,
                        color = CoopTheme.colorScheme.onSurface,
                        style = CoopTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    CoopText(
                        text = prestamo.motivo,
                        color = CoopTheme.colorScheme.onSurface,
                        style = CoopTheme.typography.bodyMedium
                    )

                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if(prestamo.codeudores != null) {
                var cantidadCodeudores = prestamo.codeudores.size
                CoopOutlinedCard(
                    elevation = 0.dp
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        CoopText(
                            text = "Codeudores",
                            fontWeight = FontWeight.Bold,
                            color = CoopTheme.colorScheme.onSurface,
                            style = CoopTheme.typography.bodyLarge,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )

                        prestamo.codeudores.forEach { codeudor ->
                            CoopText(
                                text = codeudor.nombre,
                                color = CoopTheme.colorScheme.onSurface,
                                style = CoopTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )

                            CoopText(
                                text = codeudor.dpi,
                                color = CoopTheme.colorScheme.onSurface,
                                style = CoopTheme.typography.bodyMedium
                            )

                            CoopText(
                                text = codeudor.nit,
                                color = CoopTheme.colorScheme.onSurface,
                                style = CoopTheme.typography.bodyMedium
                            )

                            CoopText(
                                text = codeudor.correo,
                                color = CoopTheme.colorScheme.onSurface,
                                style = CoopTheme.typography.bodyMedium
                            )

                            CoopText(
                                text = codeudor.telefono,
                                color = CoopTheme.colorScheme.onSurface,
                                style = CoopTheme.typography.bodyMedium
                            )

                            CoopText(
                                text = codeudor.direccion,
                                color = CoopTheme.colorScheme.onSurface,
                                style = CoopTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            //TODO: VALIDAR QUE SOLO NUMEROS SE PUEDAN METER Y QUE AGREGUE EL %
            CoopOutlinedTextField(
                value = "",
                onValueChange = {},
                label = { CoopText(text = "Interés") },
                placeholder = { CoopText(text = "") },
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = MaterialTheme.shapes.medium,
                containerColor = CoopTheme.colorScheme.surfaceVariant,
                focusedBorderColor = CoopTheme.colorScheme.primary,
                unfocusedBorderColor = CoopTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            CoopText(
                text = "Comentarios",
                color = CoopTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                style = CoopTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp),
                fontWeight = FontWeight.Bold
            )

            CoopOutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .border(1.dp, CoopTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp))
                    .height(128.dp),
            )

            Row(
                modifier = Modifier
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CoopOutlinedButton(
                    onClick = { /* TODO */ },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.height(48.dp)
                ){
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        CoopIcon(
                            Icons.Default.Close,
                            contentDescription = "Rechazar",
                            tint = CoopTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        CoopText(
                            text = "Negar",
                            style = CoopTheme.typography.bodyLarge,
                            color = CoopTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                CoopButton(
                    onClick = { /* TODO */ },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CoopTheme.colorScheme.primary,
                        contentColor = CoopTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.height(48.dp)
                ){
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        CoopIcon(
                            Icons.Default.Check,
                            contentDescription = "Aprobar",
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        CoopText(
                            text = "Aprobar",
                            style = CoopTheme.typography.bodyLarge,
                        )
                    }
                }


            }

        }




    }

}
