package app.cooperativa.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
    labelText: String? = null,
    placeholderText: String? = null,
    forceExternalLabel: Boolean = false,
    truncateSingleLine: Boolean = true,
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
    borderColor: Color? = null,
    textStyle: TextStyle = CoopTheme.typography.bodyLarge,
    contentPadding: Dp = 16.dp
) {
    // --- Simple Box + DropdownMenu implementation ---
    var expanded by remember { mutableStateOf(false) }
    var textFieldWidth by remember { mutableStateOf(0) }

    val labelComposable: (@Composable (() -> Unit))? = when {
        labelText != null -> {
            {
                Box(Modifier.fillMaxWidth()) {
                    Text(
                        text = labelText,
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis,
                        style = textStyle
                    )
                }
            }
        }
        label != null -> {
            {
                // Ensure any custom label is also constrained to the field width.
                Box(Modifier.fillMaxWidth()) { label.invoke() }
            }
        }
        else -> null
    }
    val placeholderComposable: (@Composable (() -> Unit))? = when {
        placeholderText != null -> {
            { Text(text = placeholderText, maxLines = 1, softWrap = false, overflow = TextOverflow.Ellipsis, style = textStyle) }
        }
        else -> placeholder
    }
    val anchorText = (selectedItem?.let(itemToString) ?: "")
        .replace("\n", " ")
        .replace("\r", " ")
        .trim()

    Column(modifier = modifier.fillMaxWidth()) {
        if (forceExternalLabel && (labelComposable != null)) {
            // Render label above the field to fully control width/ellipsis on small screens
            Box(Modifier.fillMaxWidth().padding(bottom = 4.dp)) {
                labelComposable.invoke()
            }
        }
        Box(Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = anchorText,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        textFieldWidth = coordinates.size.width
                    },
                readOnly = true,
                singleLine = truncateSingleLine,
                maxLines = if (truncateSingleLine) 1 else Int.MAX_VALUE,
                minLines = 1,
                label = if (forceExternalLabel) null else labelComposable,
                placeholder = {
                    placeholderComposable?.invoke()
                        ?: Text(
                            text = "Elige",
                            maxLines = 1,
                            softWrap = false,
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

                    focusedBorderColor = borderColor ?: focusedBorderColor,
                    unfocusedBorderColor = borderColor ?: unfocusedBorderColor,
                    disabledBorderColor = (borderColor ?: unfocusedBorderColor).copy(alpha = 0.3f),
                    errorBorderColor = borderColor ?: errorBorderColor,
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
        }

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
                            maxLines = if (truncateSingleLine) 1 else Int.MAX_VALUE,
                            overflow = if (truncateSingleLine) TextOverflow.Ellipsis else TextOverflow.Clip
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