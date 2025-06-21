package app.cooperativa.presentation.mainflow.socios.prestamos.loadPagare

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object LoadPagareDestination

fun NavGraphBuilder.loadPagareScreen(
    onBackClick: () -> Unit
){
    composable<LoadPagareDestination> {
        SLoadPagareRoute(onBackClick = onBackClick)
    }
}