package app.cooperativa.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.StringResource

@Immutable
data class CoopColorScheme(
    val surface: Color,
    val onSurface: Color,
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val surfaceVariant: Color
)

val LocalReplacementColorScheme = staticCompositionLocalOf {
    CoopColorScheme(
        surface = Color.Unspecified,
        onSurface = Color.Unspecified,
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        secondary = Color.Unspecified,
        onSecondary = Color.Unspecified,
        tertiary = Color.Unspecified,
        onTertiary = Color.Unspecified,
        surfaceVariant = Color.Unspecified
    )
}