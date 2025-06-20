package app.cooperativa.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Person
import app.cooperativa.presentation.mainflow.directiva.account.mainAccount.DirectivaAccountDestination
import app.cooperativa.presentation.mainflow.directiva.pagos.DPaymentNavGraph
import app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral.GeneralPaymentDestination
import app.cooperativa.presentation.mainflow.directiva.prestamos.prestamosGeneral.PrestamoNavigationDestination
import app.cooperativa.presentation.mainflow.socios.historial.SHistorialNavGraph
import app.cooperativa.presentation.mainflow.socios.historial.mainHistorial.SHistorialDestination
import app.cooperativa.presentation.mainflow.socios.prestamos.SPrestamoNavGraph
import app.cooperativa.presentation.mainflow.socios.prestamos.mainPrestamos.SPrestamoDestination

//Lista de items para socios
//TODO: Reemplazar destination x los NavGraph correspondientes
val navigationItemsSocios = listOf(
    NavBarItem(
        title = "Historial",
        selectedIcon = Icons.Default.AttachMoney,
        unselectedIcon = Icons.Outlined.AttachMoney,
        destination = SHistorialNavGraph
    ),
    NavBarItem(
        title = "Préstamos",
        selectedIcon = Icons.Filled.Payments,
        unselectedIcon = Icons.Filled.Payments,
        destination = SPrestamoNavGraph
    ),
//    NavBarItem(
//        title = "Cuenta",
//        selectedIcon = Icons.Filled.Person,
//        unselectedIcon = Icons.Outlined.Person,
//        destination =
//    )
)

// Top-level destinations, serializable objects de pantallas que pueden mostrar nav bar
val topLevelDestinationsSocios = listOf(
    SHistorialDestination::class,
    SPrestamoDestination::class
)