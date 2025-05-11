package app.cooperativa.presentation.mainflow.directiva.prestamos.prestamosGeneral

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object PrestamoNavigationDestination

fun NavGraphBuilder.boardPrestamos(
    onPrestamoClick: (Int) -> Unit
){
    composable<PrestamoNavigationDestination> {
        PrestamosRoute(
            onPendingLoanClick = onPrestamoClick
        )
    }
}