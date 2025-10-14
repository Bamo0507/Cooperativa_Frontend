package app.cooperativa.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import app.cooperativa.theme.CoopTheme
import kotlin.math.min

/**
 * Nuevo CoopDropdown:
 * - Variante con buscador (enableSearch = true)
 * - Ancho del menú = ancho del anchor
 * - Límite de ítems visibles (maxVisibleItems) con scroll
 * - Colores tomados de CoopTheme
 */
@Composable
fun <T> CoopDropdown(
    items: List<T>,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    itemToString: (T) -> String = { it.toString() },

    modifier: Modifier = Modifier,
    placeholder: String = "Elige",
    enableSearch: Boolean = false,
    enabled: Boolean = true,
    showElevation: Boolean = true,
    maxVisibleItems: Int = 4,

    // Tamaño del radio button y control de líneas del texto de la opción
    radioSize: Dp = 20.dp,
    optionMaxLines: Int = 2,

    // Permite customizar cómo se pinta cada opción
    optionContent: @Composable (option: T, isSelected: Boolean) -> Unit = { option, _ ->
        CoopText(
            text = itemToString(option),
            style = CoopTheme.typography.bodyMedium,
            // permite que textos largos bajen a otra línea (sin ellipsis),
            // y mantiene la fila centrada verticalmente
            maxLines = optionMaxLines
        )
    },

    // Colores/forma
    anchorShape: RoundedCornerShape = RoundedCornerShape(12.dp),
    anchorBackground: Color = CoopTheme.colorScheme.surfaceVariant,
    anchorBorderColor: Color = CoopTheme.colorScheme.primary,
    anchorTextColor: Color = CoopTheme.colorScheme.onSurface,
    anchorPlaceholderColor: Color = CoopTheme.colorScheme.onSurface.copy(alpha = 0.7f),
    chevronTint: Color = CoopTheme.colorScheme.onPrimary,
    menuContainerColor: Color = CoopTheme.colorScheme.surfaceVariant,
    menuBorderColor: Color = CoopTheme.colorScheme.surfaceVariant,
    selectedRowBg: Color = CoopTheme.colorScheme.primary.copy(alpha = 0.08f),
    radioSelected: Color = CoopTheme.colorScheme.onPrimary,
    radioUnselected: Color = CoopTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
) {
    var expanded by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf(TextFieldValue("")) }
    var anchorSize by remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current
    var itemHeight by remember { mutableStateOf(0.dp) }
    val itemSpacing = 4.dp
    val verticalPadding = 8.dp

    val filtered = remember(items, query.text, enableSearch) {
        if (!enableSearch) items
        else {
            val q = query.text.trim()
            if (q.isEmpty()) items else items.filter { itemToString(it).contains(q, ignoreCase = true) }
        }
    }

    val menuWidth = remember(anchorSize) { with(density) { anchorSize.width.toDp() } }
    // Cross-platform safe cap for the popup's max height (approx. half-screen)
    val screenHalf = 320.dp

    val desiredByItems =
        (itemHeight * maxVisibleItems) +
                (itemSpacing * (maxVisibleItems - 1)) +
                (verticalPadding * 2)

    val menuMaxHeight =
        if (itemHeight > 0.dp) min(desiredByItems.value, screenHalf.value).dp else screenHalf

    Box(modifier = modifier) {
        // Anchor (colapsado/expandido, con o sin buscador)
        if (!expanded) {
            DefaultCollapsedAnchor(
                text = selectedItem?.let(itemToString),
                placeholder = placeholder,
                enabled = enabled,
                shape = anchorShape,
                background = anchorBackground,
                borderColor = anchorBorderColor,
                textColor = anchorTextColor,
                placeholderColor = anchorPlaceholderColor,
                chevronTint = chevronTint,
                expanded = false,
                onClick = { if (enabled) expanded = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { anchorSize = it.size },
                showElevation = showElevation
            )
        } else {
            if (enableSearch) {
                SearchAnchor(
                    value = query,
                    onValueChange = { query = it },
                    onClear = { query = TextFieldValue("") },
                    onDismiss = { expanded = false },
                    shape = anchorShape,
                    background = anchorBackground,
                    borderColor = anchorBorderColor,
                    textColor = anchorTextColor,
                    placeholderColor = anchorPlaceholderColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { anchorSize = it.size },
                    showElevation = showElevation
                )
            } else {
                DefaultCollapsedAnchor(
                    text = selectedItem?.let(itemToString) ?: placeholder,
                    placeholder = placeholder,
                    enabled = enabled,
                    shape = anchorShape,
                    background = anchorBackground,
                    borderColor = anchorBorderColor,
                    textColor = anchorTextColor,
                    placeholderColor = anchorPlaceholderColor,
                    chevronTint = chevronTint,
                    expanded = true,
                    onClick = { if (enabled) expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { anchorSize = it.size },
                    showElevation = showElevation
                )
            }
        }

        // Propiedades del popup (cuando hay buscador, no se cierra al tocar fuera)
        val popupProps = remember(enableSearch) {
            if (enableSearch) {
                PopupProperties(
                    focusable = false,
                    dismissOnClickOutside = false,
                    dismissOnBackPress = false
                )
            } else {
                PopupProperties(
                    focusable = true,
                    dismissOnClickOutside = true,
                    dismissOnBackPress = true
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                if (enableSearch) query = TextFieldValue("")
            },
            modifier = Modifier
                .width(menuWidth)
                .border(0.5.dp, menuBorderColor, RoundedCornerShape(12.dp))
                .background(menuContainerColor),
            containerColor = menuContainerColor,
            properties = popupProps
        ) {
            if (filtered.isEmpty()) {
                DropdownMenuItem(
                    text = {
                        CoopText(
                            text = "Sin resultados",
                            color = anchorTextColor.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    onClick = { /* no-op */ },
                    enabled = false,
                    contentPadding = PaddingValues(vertical = 8.dp)
                )
            } else {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = menuMaxHeight)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(itemSpacing)
                ) {
                    filtered.forEachIndexed { index, option ->
                        val isSelected = selectedItem == option
                        DropdownMenuItem(
                            text = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(Modifier.weight(1f)) {
                                        optionContent(option, isSelected)
                                    }
                                    Spacer(Modifier.width(8.dp))
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = {
                                            onItemSelected(option)
                                            expanded = false
                                            if (enableSearch) query = TextFieldValue("")
                                        },
                                        modifier = Modifier.size(radioSize),
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = radioSelected,
                                            unselectedColor = radioUnselected
                                        )
                                    )
                                }
                            },
                            onClick = {
                                onItemSelected(option)
                                expanded = false
                                if (enableSearch) query = TextFieldValue("")
                            },
                            modifier = Modifier
                                .then(
                                    if (index == 0 && itemHeight == 0.dp) {
                                        Modifier.onGloballyPositioned { coords ->
                                            itemHeight = with(density) { coords.size.height.toDp() }
                                        }
                                    } else Modifier
                                )
                                .fillMaxWidth()
                                .background(if (isSelected) selectedRowBg else Color.Transparent)
                                .padding(vertical = 4.dp)
                        )
                        if (index < filtered.lastIndex) Spacer(Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

/* ——— Subcomposables ——— */

@Composable
private fun AnchorContainer(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    background: Color,
    borderColor: Color,
    showElevation: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    val borderStroke = 0.5.dp

    Box(
        modifier = modifier
            .defaultMinSize(minHeight = 48.dp)
            .background(background, shape)
            .border(borderStroke, borderColor, shape)
            .clickable(
                enabled = enabled,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp)
                .defaultMinSize(minHeight = 48.dp),
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Composable
private fun DefaultCollapsedAnchor(
    text: String?,
    placeholder: String,
    enabled: Boolean,
    shape: RoundedCornerShape,
    background: Color,
    borderColor: Color,
    textColor: Color,
    placeholderColor: Color,
    chevronTint: Color,
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showElevation: Boolean = true
) {
    AnchorContainer(
        enabled = enabled,
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        background = background,
        borderColor = borderColor,
        showElevation = showElevation
    ) {
        val isPlaceholder = text.isNullOrEmpty() || text == placeholder
        CoopText(
            text = text ?: placeholder,
            color = if (isPlaceholder) placeholderColor else textColor,
            style = CoopTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 12.dp),
            maxLines = 1
        )
        Spacer(Modifier.width(8.dp))
        androidx.compose.material3.Icon(
            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = null,
            tint = chevronTint
        )
    }
}

@Composable
private fun SearchAnchor(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onClear: () -> Unit,
    onDismiss: () -> Unit,
    shape: RoundedCornerShape,
    background: Color,
    borderColor: Color,
    textColor: Color,
    placeholderColor: Color,
    modifier: Modifier = Modifier,
    showElevation: Boolean = true
) {
    val focusRequester = remember { FocusRequester() }
    AnchorContainer(
        enabled = false,
        onClick = {},
        modifier = modifier,
        shape = shape,
        background = background,
        borderColor = borderColor,
        showElevation = showElevation
    ) {
        androidx.compose.material3.Icon(Icons.Filled.Search, contentDescription = "Buscar", tint = textColor)
        Spacer(Modifier.width(8.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = CoopTheme.typography.bodyLarge.copy(color = textColor),
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 12.dp)
                .focusRequester(focusRequester),
            decorationBox = { inner ->
                Box(Modifier.fillMaxWidth()) {
                    if (value.text.isEmpty()) {
                        CoopText(
                            text = "Buscar…",
                            style = CoopTheme.typography.bodyLarge,
                            color = placeholderColor
                        )
                    }
                    inner()
                }
            },
        )
        Spacer(Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { if (value.text.isNotEmpty()) onClear() else onDismiss() },
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = if (value.text.isNotEmpty()) "Limpiar" else "Cerrar",
                tint = textColor
            )
        }
    }
}

/* ——— Overload de compatibilidad ———
 * Para llamadas existentes que pasaban label/placeholder composables.
 * Internamente usamos solo 'placeholder' String y el resto de props por defecto.
 */
@Deprecated("Usa la nueva firma con placeholder String y enableSearch")
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
    // colores heredados ignorados en la nueva versión; conservamos firma para no romper
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(12.dp),
    containerColor: Color = CoopTheme.colorScheme.surface,
    contentColor: Color = CoopTheme.colorScheme.onSurface,
    cursorColor: Color = CoopTheme.colorScheme.primary,
    focusedBorderColor: Color = CoopTheme.colorScheme.primary,
    unfocusedBorderColor: Color = CoopTheme.colorScheme.surfaceVariant,
    errorBorderColor: Color = CoopTheme.colorScheme.error,
    borderColor: Color? = null,
    textStyle: androidx.compose.ui.text.TextStyle = CoopTheme.typography.bodyLarge,
    contentPadding: androidx.compose.ui.unit.Dp = 16.dp
) {
    val ph = placeholderText ?: run {
        // Si nos pasaron un placeholder composable sencillo tipo CoopText("Elige")
        // no podemos leer su String; caemos al default.
        "Elige"
    }
    CoopDropdown(
        items = items,
        selectedItem = selectedItem,
        onItemSelected = onItemSelected,
        itemToString = itemToString,
        modifier = modifier,
        placeholder = ph,
        enableSearch = false,
        enabled = enabled
    )
}
