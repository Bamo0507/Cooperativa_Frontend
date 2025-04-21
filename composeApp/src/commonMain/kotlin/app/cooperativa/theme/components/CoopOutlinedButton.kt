package app.cooperativa.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import app.cooperativa.theme.CoopTheme


@Composable
private fun outlinedButtonBorder(enabled: Boolean = true): BorderStroke =
    BorderStroke(
        width = 1.dp,
        color = if (enabled) {
            CoopTheme.colorScheme.onSurface
        } else {
            CoopTheme.colorScheme.onSurface.copy(alpha = 0.25f)
        }
    )

@Composable
fun LDOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp),
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(
        containerColor = CoopTheme.colorScheme.surface,
        contentColor = CoopTheme.colorScheme.onSurface,
        disabledContainerColor = CoopTheme.colorScheme.surface.copy(alpha = 0.25f),
        disabledContentColor = CoopTheme.colorScheme.onSurface.copy(alpha = 0.25f)
    ),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = outlinedButtonBorder(enabled),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content
    )
}
