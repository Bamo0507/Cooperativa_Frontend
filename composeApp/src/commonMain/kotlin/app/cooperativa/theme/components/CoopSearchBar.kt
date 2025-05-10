package app.cooperativa.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.cooperativa.theme.CoopTheme

@Composable
fun CoopSearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = {
        CoopIcon(
            imageVector = Icons.Default.Search,
            contentDescription = "Buscar",
            tint = CoopTheme.colorScheme.onSurface
        )
    }
) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        placeholder = {
            CoopText(
                text = placeholder,
                style = CoopTheme.typography.bodyLarge.copy(color = CoopTheme.colorScheme.onSurface)
            )
        },
        leadingIcon = leadingIcon,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(CoopTheme.colorScheme.primary.copy(alpha = 0.4f))
            .fillMaxWidth()
            .shadow(1.dp, shape = RoundedCornerShape(50)),
        colors = TextFieldDefaults.colors(
            focusedTextColor = CoopTheme.colorScheme.onSurface,
            unfocusedTextColor = CoopTheme.colorScheme.onSurface,
            disabledTextColor = CoopTheme.colorScheme.onSurface.copy(alpha = 0.4f),

            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,

            cursorColor = CoopTheme.colorScheme.onSurface,

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,

            focusedLeadingIconColor = CoopTheme.colorScheme.onSurface,
            unfocusedLeadingIconColor = CoopTheme.colorScheme.onSurface,
            disabledLeadingIconColor = CoopTheme.colorScheme.onSurface.copy(alpha = 0.4f),

            focusedPlaceholderColor = CoopTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            unfocusedPlaceholderColor = CoopTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            disabledPlaceholderColor = CoopTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        ),
        singleLine = true
    )
}