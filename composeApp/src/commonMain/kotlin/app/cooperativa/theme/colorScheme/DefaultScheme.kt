package app.cooperativa.theme.colorScheme

import androidx.compose.ui.graphics.Color
import app.cooperativa.theme.CoopColorScheme

val LightColorScheme = CoopColorScheme(
    surface = Color(0xF5F5F5),
    onSurface = Color(0x2A2F35),
    primary = Color(0xD0E7E8),
    onPrimary = Color(0x21434A),
    secondary = Color(0x81AFDB),
    onSecondary = Color(0x0B2A41),
    tertiary = Color( 0x043259),
    onTertiary = Color(0xE1F3FF)
)

val DarkColorScheme = CoopColorScheme(
    surface = Color(0x121212),
    primary = Color(0x9AC6F8),
    secondary = Color(0x2C5A87),
    tertiary = Color(0x3A5F60),
    onSurface = Color(0xD6D9DE),
    onPrimary = Color(0x112437),
    onSecondary = Color(0xCBE5FF),
    onTertiary = Color(0xD3ECEC)
)