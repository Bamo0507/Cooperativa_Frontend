package app.cooperativa.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.cooperativa.theme.CoopTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoopOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    readOnly: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    shape: Shape = MaterialTheme.shapes.medium,
    containerColor: Color = CoopTheme.colorScheme.surface,
    contentColor: Color = CoopTheme.colorScheme.onSurface,
    cursorColor: Color = CoopTheme.colorScheme.primary,
    focusedBorderColor: Color = CoopTheme.colorScheme.primary,
    unfocusedBorderColor: Color = CoopTheme.colorScheme.surfaceVariant,
    errorBorderColor: Color = CoopTheme.colorScheme.error,
    textStyle: TextStyle = CoopTheme.typography.bodyLarge,
    contentPadding: Dp = 16.dp
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        enabled = enabled,
        singleLine = singleLine,
        maxLines = maxLines,
        readOnly = readOnly,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        textStyle = textStyle,
        shape = shape,
        colors = TextFieldDefaults.colors(
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

            focusedIndicatorColor = focusedBorderColor,
            unfocusedIndicatorColor = unfocusedBorderColor,
            disabledIndicatorColor = unfocusedBorderColor.copy(alpha = 0.3f),
            errorIndicatorColor = errorBorderColor,

            focusedLeadingIconColor = contentColor,
            unfocusedLeadingIconColor = contentColor,
            disabledLeadingIconColor = contentColor.copy(alpha = 0.3f),
            errorLeadingIconColor = CoopTheme.colorScheme.error,

            focusedTrailingIconColor = contentColor,
            unfocusedTrailingIconColor = contentColor,
            disabledTrailingIconColor = contentColor.copy(alpha = 0.3f),
            errorTrailingIconColor = CoopTheme.colorScheme.error,

            focusedLabelColor = contentColor,
            unfocusedLabelColor = contentColor.copy(alpha = 0.7f),
            disabledLabelColor = contentColor.copy(alpha = 0.4f),
            errorLabelColor = CoopTheme.colorScheme.error,

            focusedPlaceholderColor = contentColor.copy(alpha = 0.6f),
            unfocusedPlaceholderColor = contentColor.copy(alpha = 0.6f),
            disabledPlaceholderColor = contentColor.copy(alpha = 0.3f),
            errorPlaceholderColor = contentColor.copy(alpha = 0.6f)
        )
    )
}
