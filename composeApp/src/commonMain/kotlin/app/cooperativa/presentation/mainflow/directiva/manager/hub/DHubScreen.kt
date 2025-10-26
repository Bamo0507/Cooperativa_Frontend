package app.cooperativa.presentation.mainflow.directiva.manager.hub

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.RequestQuote
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopButton
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar

@Composable
fun DHubRoute(
    onFineClick: () -> Unit,
    onLoanClick: () -> Unit
){
    DHubScreen(
        onFineClick = onFineClick,
        onLoanClick = onLoanClick
    )
}

@Composable
fun DHubScreen(
    onFineClick: () -> Unit,
    onLoanClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CoopTopBar(
                title = "¿Qué deseas registrar?"
            )
        },
        containerColor = CoopTheme.colorScheme.surface
    ){ padding ->
        Column(
            modifier = modifier
                .background(CoopTheme.colorScheme.surface)
                .fillMaxSize()
        ){
            // Box for fine form
            Box(
                modifier = Modifier.weight(1f).fillMaxWidth()
                    .background(CoopTheme.colorScheme.secondary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ){
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CoopIcon(
                        Icons.Outlined.ReceiptLong,
                        contentDescription = "Multa",
                        tint = CoopTheme.colorScheme.onSecondary,
                        modifier = Modifier.size(72.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CoopButton(
                        onClick = onFineClick
                    ){
                        CoopText(
                            text = "Registrar Multa",
                            style = CoopTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

            }

            HorizontalDivider(
                color = CoopTheme.colorScheme.onSecondary
            )

            // Box for loan form
            Box(
                modifier = Modifier
                    .weight(1f).fillMaxWidth()
                    .background(CoopTheme.colorScheme.secondary.copy(alpha = 0.17f))
                ,
                contentAlignment = Alignment.Center
            ){
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CoopIcon(
                        Icons.Outlined.RequestQuote,
                        contentDescription = "Prestamo",
                        tint = CoopTheme.colorScheme.onSecondary,
                        modifier = Modifier.size(72.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CoopButton(
                        onClick = onLoanClick,
                    ){
                        CoopText(
                            text = "Registrar Préstamo",
                            style = CoopTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }
            }
        }

    }

}
