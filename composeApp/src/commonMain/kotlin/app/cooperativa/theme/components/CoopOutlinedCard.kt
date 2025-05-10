package app.cooperativa.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.cooperativa.theme.CoopTheme

@Composable
fun CoopOutlinedCard(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    containerColor: Color = CoopTheme.colorScheme.surfaceVariant,
    contentColor: Color = CoopTheme.colorScheme.onSurface,
    elevation: Dp = 4.dp,
    content: @Composable () -> Unit
) {
    OutlinedCard(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        colors = CardDefaults.outlinedCardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        border = BorderStroke(width = 1.dp, color = CoopTheme.colorScheme.primary),
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = elevation
        )
    ) {
        content()
    }
}