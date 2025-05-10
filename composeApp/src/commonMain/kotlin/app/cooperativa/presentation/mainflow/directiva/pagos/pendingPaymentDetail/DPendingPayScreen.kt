package app.cooperativa.presentation.mainflow.directiva.pagos.pendingPaymentDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.cooperativa.data.localdb.PaymentMockData
import app.cooperativa.data.model.dto.Payment
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopButton
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopOutlinedButton
import app.cooperativa.theme.components.CoopOutlinedCard
import app.cooperativa.theme.components.CoopOutlinedTextField
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar
import app.cooperativa.theme.utils.dateToString

@Composable
fun DPendingPayRoute() {
    // TODO: change when navigation is implemented
    val payment = PaymentMockData.getPaymentById(2)

    DPendingPayScreen(
        payment = payment!!,
        onBackClick = {}
    )
}

@Composable
fun DPendingPayScreen(
    payment: Payment,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = {
            CoopTopBar(
                title = payment.paymentName,
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
                .padding(vertical = 6.dp, horizontal = 8.dp)
                .verticalScroll(state = rememberScrollState(), enabled = true)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            if(payment.quotas?.isNotEmpty() == true) {
                CoopOutlinedCard(
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
                ){
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        CoopText(
                            text = "Cuotas",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start,
                            style = CoopTheme.typography.bodyLarge,
                            color = CoopTheme.colorScheme.onSecondary
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        payment.quotas.forEach { quota ->
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Column {
                                    CoopText(
                                        text = dateToString(quota.date),
                                        textAlign = TextAlign.Start,
                                        style = CoopTheme.typography.bodyMedium,
                                    )
                                    CoopText(
                                        text = quota.userName,
                                        textAlign = TextAlign.Start,
                                        style = CoopTheme.typography.bodyMedium,
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))

                                CoopText(
                                    text = "Q${quota.amount}",
                                    textAlign = TextAlign.End,
                                    style = CoopTheme.typography.bodyMedium,
                                    color = CoopTheme.colorScheme.onSecondary
                                )
                            }
                        }
                    }
                }
            }

            if(payment.loanPayments?.isNotEmpty() == true) {
                CoopOutlinedCard(
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
                ){
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        CoopText(
                            text = "Préstamos",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start,
                            style = CoopTheme.typography.bodyLarge,
                            color = CoopTheme.colorScheme.onSecondary
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        payment.loanPayments.forEach { loanPayment ->
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Column {
                                    CoopText(
                                        text = dateToString(loanPayment.date),
                                        textAlign = TextAlign.Start,
                                        style = CoopTheme.typography.bodyMedium,
                                    )
                                    CoopText(
                                        text = loanPayment.loanName,
                                        textAlign = TextAlign.Start,
                                        style = CoopTheme.typography.bodyMedium,
                                    )
                                    CoopText(
                                        text = loanPayment.userName,
                                        textAlign = TextAlign.Start,
                                        style = CoopTheme.typography.bodyMedium,
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))

                                CoopText(
                                    text = "Q${loanPayment.amountPayed}",
                                    textAlign = TextAlign.End,
                                    style = CoopTheme.typography.bodyMedium,
                                    color = CoopTheme.colorScheme.onSecondary
                                )
                            }
                        }

                    }
                }
            }

            if (payment.finePayments?.isNotEmpty() == true) {
                CoopOutlinedCard(
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
                ){
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        CoopText(
                            text = "Multas",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start,
                            style = CoopTheme.typography.bodyLarge,
                            color = CoopTheme.colorScheme.onSecondary
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        payment.finePayments.forEach { finePayment ->
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Column {
                                    CoopText(
                                        text = finePayment.fineName,
                                        textAlign = TextAlign.Start,
                                        style = CoopTheme.typography.bodyMedium,
                                    )
                                    CoopText(
                                        text = finePayment.user,
                                        textAlign = TextAlign.Start,
                                        style = CoopTheme.typography.bodyMedium,
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))

                                CoopText(
                                    text = "${finePayment.amount}",
                                    textAlign = TextAlign.End,
                                    style = CoopTheme.typography.bodyMedium,
                                    color = CoopTheme.colorScheme.onSecondary
                                )
                            }
                        }
                    }
                }
            }

            if (payment.contributionPayments?.isNotEmpty() == true) {
                CoopOutlinedCard(
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
                ){
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        CoopText(
                            text = "Aportes",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start,
                            style = CoopTheme.typography.bodyLarge,
                            color = CoopTheme.colorScheme.onSecondary
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        payment.contributionPayments.forEach { contributionPayment ->
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                CoopText(
                                    text = contributionPayment.user,
                                    textAlign = TextAlign.Start,
                                    style = CoopTheme.typography.bodyMedium,
                                )
                                Spacer(modifier = Modifier.weight(1f))

                                CoopText(
                                    text = "${contributionPayment.amount}",
                                    textAlign = TextAlign.End,
                                    style = CoopTheme.typography.bodyMedium,
                                    color = CoopTheme.colorScheme.onSecondary
                                )
                            }
                        }
                    }
                }
            }

            //TODO: Hacer un preview de la imagen a mostrar cuando se de ver boleta
            // De momento con un color de fonto
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(16.dp)
                    .background(CoopTheme.colorScheme.surface, shape = RoundedCornerShape(16.dp))
                    .border(1.dp, CoopTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ){
                CoopIcon(
                    Icons.Default.Wallpaper,
                    contentDescription = "Imagen momentanea",
                    tint = CoopTheme.colorScheme.tertiary,
                    modifier = Modifier.size(100.dp)
                )
            }

            // Boton de ver boleta
            CoopButton(
                onClick = {
                    /* TODO */
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CoopTheme.colorScheme.tertiary,
                )
            ){
                CoopText(
                    text = "Ver Boleta",
                    style = CoopTheme.typography.bodyLarge,
                    color = CoopTheme.colorScheme.onTertiary
                )
            }

            // Comment section
            CoopText(
                text = "Comentarios",
                color = CoopTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                style = CoopTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            CoopOutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .border(1.dp, CoopTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp))
                    .height(128.dp),
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp),
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
                        containerColor = CoopTheme.colorScheme.tertiary,
                        contentColor = CoopTheme.colorScheme.onTertiary
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
                            tint = CoopTheme.colorScheme.onTertiary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        CoopText(
                            text = "Aprobar",
                            style = CoopTheme.typography.bodyLarge,
                            color = CoopTheme.colorScheme.onTertiary
                        )
                    }
                }


            }



        }

    }

}