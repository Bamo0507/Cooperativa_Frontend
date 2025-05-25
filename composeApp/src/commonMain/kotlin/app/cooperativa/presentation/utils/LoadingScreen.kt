package app.cooperativa.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopText

@Composable
fun LoadingScreen(modifier: Modifier = Modifier, message: String? = null) {
    Box(
        modifier
            .fillMaxSize()
            .background(CoopTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = CoopTheme.colorScheme.primary)
            message?.let {
                Spacer(Modifier.height(12.dp))
                CoopText(text = it, style = CoopTheme.typography.bodyLarge, color = CoopTheme.colorScheme.onSurface)
            }
        }
    }
}