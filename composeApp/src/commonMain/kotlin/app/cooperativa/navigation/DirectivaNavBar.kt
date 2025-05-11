package app.cooperativa.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopText

/**
 * Barra de navegación inferior, personalizada según tus temas y componentes.
 *
 * @param checkItemSelected determina si el destino está seleccionado.
 * @param onNavItemClick    callback con el destino seleccionado.
 */
@Composable
fun DirectivaBottomNavBar(
    checkItemSelected: (Any) -> Boolean,
    onNavItemClick: (Any) -> Unit
) {
    NavigationBar(
        containerColor = CoopTheme.colorScheme.primary,
        tonalElevation = 4.dp
    ) {
        navigationItemsDirectiva.forEach { navItem ->
            val isSelected = checkItemSelected(navItem.destination)

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavItemClick(navItem.destination) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor      = CoopTheme.colorScheme.surface,
                    selectedIconColor   = CoopTheme.colorScheme.onPrimary,
                    unselectedIconColor = CoopTheme.colorScheme.secondary,
                    selectedTextColor   = CoopTheme.colorScheme.onSurface,
                    unselectedTextColor = CoopTheme.colorScheme.onSurface
                ),
                alwaysShowLabel = true,
                icon = {
                    CoopIcon(
                        imageVector        = if (isSelected) navItem.selectedIcon else navItem.unselectedIcon,
                        contentDescription = navItem.title,
                        tint               = (
                                if (isSelected)
                                    CoopTheme.colorScheme.onSecondary
                                else
                                    CoopTheme.colorScheme.onPrimary
                                ),
                        modifier           = Modifier.size(24.dp)
                    )
                },
                label = {
                    CoopText(
                        text       = navItem.title,
                        color      = CoopTheme.colorScheme.onPrimary,
                        fontSize   = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            )
        }
    }
}