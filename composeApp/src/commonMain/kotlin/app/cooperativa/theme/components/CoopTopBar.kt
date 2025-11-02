package app.cooperativa.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.cooperativa.theme.CoopTheme

@Composable
fun CoopTopBar(
    title: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = CoopTheme.colorScheme.primary,
    contentColor: Color = CoopTheme.colorScheme.onPrimary,
    leadingArrow: Boolean = false,
    onBackClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)              // altura mínima tipo TopAppBar
                .padding(horizontal = 16.dp)         // margen lateral global
                .padding(vertical = 12.dp),          // respiro vertical
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                if (leadingArrow) {
                    CoopIcon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = contentColor,
                        modifier = Modifier.clickable { onBackClick() }
                    )
                }
            }

            // Título — ocupa el espacio flexible
            CoopText(
                text = title,
                color = contentColor,
                fontWeight = FontWeight.Bold,
                style = CoopTheme.typography.headlineSmall,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp, end = 8.dp),      // evita que el texto toque los bordes internos
                textAlign = if (leadingArrow) TextAlign.Start else TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}