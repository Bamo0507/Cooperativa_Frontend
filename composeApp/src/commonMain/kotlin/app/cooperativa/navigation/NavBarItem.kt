package app.cooperativa.navigation

import androidx.compose.ui.graphics.vector.ImageVector

// Clase de datos para los items de la barra de navegación
data class NavBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val destination: Any
)
