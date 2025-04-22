package app.cooperativa.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopText
import androidx.compose.ui.text.font.FontWeight

@Composable
fun SociosNavigationBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val items = listOf(
        Icons.Default.AttachMoney to "Historial",
        Icons.Default.Folder      to "Préstamos",
        Icons.Default.Archive     to "FAQs",
        Icons.Default.Person      to "Cuenta"
    )

    NavigationBar(
        containerColor = CoopTheme.colorScheme.primary,
        tonalElevation = 4.dp
    ) {
        items.forEachIndexed { index, (icon, label) ->
            val isSelected = index == selectedIndex

            NavigationBarItem(
                selected = isSelected,
                onClick  = { onItemSelected(index) },
                colors   = NavigationBarItemDefaults.colors(
                    indicatorColor      = CoopTheme.colorScheme.surface,
                    selectedIconColor   = CoopTheme.colorScheme.onPrimary,
                    unselectedIconColor = CoopTheme.colorScheme.secondary,
                    selectedTextColor   = CoopTheme.colorScheme.onSurface,
                    unselectedTextColor = CoopTheme.colorScheme.onSurface
                ),
                alwaysShowLabel = true,
                icon = {
                    if (isSelected) {
                        CoopIcon(
                            imageVector      = icon,
                            contentDescription = label,
                            tint             = CoopTheme.colorScheme.tertiary,
                            modifier         = Modifier.size(24.dp)
                        )

                    } else {
                        CoopIcon(
                            imageVector        = icon,
                            contentDescription = label,
                            tint               = CoopTheme.colorScheme.onPrimary,
                            modifier           = Modifier.size(24.dp)
                        )
                    }
                },
                label = {
                    CoopText(
                        text       = label,
                        color      = CoopTheme.colorScheme.onPrimary,
                        fontSize   = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            )
        }
    }
}