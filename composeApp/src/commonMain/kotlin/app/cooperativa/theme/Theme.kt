package app.cooperativa.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import app.cooperativa.theme.colorScheme.DarkColorScheme
import app.cooperativa.theme.colorScheme.LightColorScheme
import cooperativa.composeapp.theme.PoppinsTypography

@Composable
fun CoopTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(
        LocalReplacementColorScheme provides colors
    ) {
        MaterialTheme(
            typography = PoppinsTypography(),
            content = content
        )
    }
}

object CoopTheme {
    val colorScheme: CoopColorScheme
        @Composable
        get() = LocalReplacementColorScheme.current

    val typography: Typography
        @Composable
        get() = MaterialTheme.typography
}