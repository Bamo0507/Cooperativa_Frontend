package app.cooperativa.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import app.cooperativa.presentation.mainflow.directiva.account.DAccountNavGraph
import app.cooperativa.presentation.mainflow.directiva.account.mainAccount.DirectivaAccountDestination
import app.cooperativa.presentation.mainflow.directiva.pagos.DPaymentNavGraph
import app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral.GeneralPaymentDestination
import app.cooperativa.presentation.mainflow.directiva.prestamos.DLoanNavGraph
import app.cooperativa.presentation.mainflow.directiva.prestamos.prestamosGeneral.PrestamoNavigationDestination

// Clase de datos para los items de la barra de navegación
data class DirectivaNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val destination: Any
)

// Lista de items, todos apuntan por ahora a DPaymentNavGraph
val navigationItemsDirectiva = listOf(
    DirectivaNavItem(
        title = "Pagos",
        selectedIcon = Icons.Filled.AttachMoney,
        unselectedIcon = Icons.Outlined.AttachMoney,
        destination = DPaymentNavGraph
    ),
    DirectivaNavItem(
        title = "Préstamos",
        selectedIcon = Icons.Filled.Folder,
        unselectedIcon = Icons.Outlined.Folder,
        destination = DLoanNavGraph
    ),
    DirectivaNavItem(
        title = "Cuenta",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        destination = DAccountNavGraph
    )
)

// Top-level destinations, los dejas temporalmente todos iguales
val topLevelDestinationsDirectiva = listOf(
    GeneralPaymentDestination::class,
    PrestamoNavigationDestination::class,
    DirectivaAccountDestination::class
)