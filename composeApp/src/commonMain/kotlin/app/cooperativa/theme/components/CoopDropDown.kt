package app.cooperativa.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.cooperativa.theme.CoopTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.toSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CoopDropdown(
    items: List<T>,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    itemToString: (T) -> String = { it.toString() },
    shape: Shape = MaterialTheme.shapes.medium,
    containerColor: Color = CoopTheme.colorScheme.surfaceVariant,
    contentColor: Color = CoopTheme.colorScheme.onSurface,
    cursorColor: Color = CoopTheme.colorScheme.primary,
    focusedBorderColor: Color = CoopTheme.colorScheme.primary,
    unfocusedBorderColor: Color = CoopTheme.colorScheme.surfaceVariant,
    errorBorderColor: Color = CoopTheme.colorScheme.error,
    textStyle: TextStyle = CoopTheme.typography.bodyLarge,
    contentPadding: Dp = 16.dp
) {
    // --- Simple Box + DropdownMenu implementation ---
    var expanded by remember { mutableStateOf(false) }
    var textFieldWidth by remember { mutableStateOf(0) }

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedItem?.let(itemToString) ?: "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()

                .onGloballyPositioned { coordinates ->
                    textFieldWidth = coordinates.size.width
                },
            readOnly = true,
            singleLine = true,
            maxLines = 1,
            label = label,
            placeholder = {
                placeholder?.invoke()
                    ?: Text(
                        text = "Elige",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = textStyle
                    )
            },
            isError = isError,
            enabled = enabled,
            shape = shape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = contentColor,
                unfocusedTextColor = contentColor,
                disabledTextColor = contentColor.copy(alpha = 0.4f),
                errorTextColor = CoopTheme.colorScheme.error,

                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor.copy(alpha = 0.3f),
                errorContainerColor = containerColor,

                cursorColor = cursorColor,
                errorCursorColor = CoopTheme.colorScheme.error,

                focusedBorderColor = focusedBorderColor,
                unfocusedBorderColor = unfocusedBorderColor,
                disabledBorderColor = unfocusedBorderColor.copy(alpha = 0.3f),
                errorBorderColor = errorBorderColor,
            ),
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    tint = contentColor
                )
            }
        )

        // Invisible clickable layer to toggle menu, ensuring the TextField's internal
        // pointerInput doesn't consume the click.
        Box(
            Modifier
                .matchParentSize()
                .clickable { expanded = !expanded }
        )

        val density = LocalDensity.current
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(
                    if (textFieldWidth > 0)
                        with(LocalDensity.current) { textFieldWidth.toDp() }
                    else
                        200.dp        // fallback width before measurement
                )
                .background(containerColor)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = itemToString(item),
                            style = textStyle,
                            color = CoopTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = CoopTheme.colorScheme.onSurface
                    )
                )
            }
        }
    }
}