package app.cooperativa.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
    contentColor: Color = CoopTheme.colorScheme.onPrimary
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(vertical = 16.dp)
    ) {
        CoopText(
            text = title,
            color = contentColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = CoopTheme.typography.headlineSmall
        )
    }
}