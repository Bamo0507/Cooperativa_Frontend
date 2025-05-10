package app.cooperativa.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.cooperativa.theme.CoopTheme

@Composable
fun CoopTopBar(
    title: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = CoopTheme.colorScheme.primary,
    contentColor: Color = CoopTheme.colorScheme.onPrimary,
    leadingArrow: Boolean = false,
    onBackClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(vertical = 16.dp)
    ) {
        // Flecha de retroceso opcional
        if (leadingArrow) {
            CoopIcon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = contentColor,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
                    .clickable { onBackClick() }
            )
        }

        // Título, alineado a start si hay flecha, o centrado si no
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = if (leadingArrow) 56.dp else 0.dp)
        ) {
            CoopText(
                text = title,
                color = contentColor,
                fontWeight = FontWeight.Bold,
                style = CoopTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = if (leadingArrow) TextAlign.Start else TextAlign.Center
            )
        }
    }
}