package app.cooperativa.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TableRows
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.TableRows
import app.cooperativa.presentation.mainflow.directiva.account.DAccountNavGraph
import app.cooperativa.presentation.mainflow.directiva.account.mainAccount.DirectivaAccountDestination
import app.cooperativa.presentation.mainflow.directiva.manager.DManagerNavGraph
import app.cooperativa.presentation.mainflow.directiva.manager.hub.DHubDestination
import app.cooperativa.presentation.mainflow.directiva.pagos.DPaymentNavGraph
import app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral.GeneralPaymentDestination
import app.cooperativa.presentation.mainflow.directiva.prestamos.DLoanNavGraph
import app.cooperativa.presentation.mainflow.directiva.prestamos.prestamosGeneral.PrestamoNavigationDestination

// Lista de items
val navigationItemsDirectiva = listOf(
    NavBarItem(
        title = "Pagos",
        selectedIcon = Icons.Filled.AttachMoney,
        unselectedIcon = Icons.Outlined.AttachMoney,
        destination = DPaymentNavGraph
    ),
    NavBarItem(
        title = "Préstamos",
        selectedIcon = Icons.Filled.Folder,
        unselectedIcon = Icons.Outlined.Folder,
        destination = DLoanNavGraph
    ),
    NavBarItem(
        title = "Gestión",
        selectedIcon = Icons.Filled.TableRows,
        unselectedIcon = Icons.Outlined.TableRows,
        destination = DManagerNavGraph
    ),
    NavBarItem(
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
    DirectivaAccountDestination::class,
    DHubDestination::class
)