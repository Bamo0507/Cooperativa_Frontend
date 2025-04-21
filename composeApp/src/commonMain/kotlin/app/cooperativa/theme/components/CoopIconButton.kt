package app.cooperativa.theme.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cooperativa.theme.CoopTheme

@Composable
fun LDIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(
        containerColor = CoopTheme.colorScheme.surface,
        contentColor = CoopTheme.colorScheme.onSurface,
        disabledContainerColor = CoopTheme.colorScheme.surface.copy(alpha = 0.25f),
        disabledContentColor = CoopTheme.colorScheme.onSurface.copy(alpha = 0.25f)
    ),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource,
    ) {
        content()
    }
}
