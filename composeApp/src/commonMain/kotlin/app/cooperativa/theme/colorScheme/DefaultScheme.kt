package app.cooperativa.theme.colorScheme

import androidx.compose.ui.graphics.Color
import app.cooperativa.theme.CoopColorScheme

val LightColorScheme = CoopColorScheme(
    surface   = Color(0xFFFFFFFF),
    onSurface = Color(0xFF2A2F35),
    primary   = Color(0xFFD0E7E8),
    onPrimary = Color(0xFF21434A),
    secondary   = Color(0xFF81AFDB),
    onSecondary = Color(0xFF0B2A41),
    tertiary   = Color(0xFF043259),
    onTertiary = Color(0xFFE1F3FF),
    surfaceVariant = Color(0xFFF5F5F5),
    error = Color(0xFFB00020)
)

val DarkColorScheme = CoopColorScheme(
    surface = Color(0xFF121212),
    primary = Color(0xFF9AC6F8),
    secondary = Color(0xFF2C5A87),
    tertiary = Color(0xFF3A5F60),
    onSurface = Color(0xFFD6D9DE),
    onPrimary = Color(0xFF112437),
    onSecondary = Color(0xFFCBE5FF),
    onTertiary = Color(0xFFD3ECEC),
    surfaceVariant = Color(0xFF1e1e1e),
    error = Color(0xCF6679)
)