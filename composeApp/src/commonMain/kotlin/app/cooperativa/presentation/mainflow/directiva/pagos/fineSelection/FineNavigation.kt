package app.cooperativa.presentation.mainflow.directiva.pagos.fineSelection

import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class EditFineDestination(val accessKey: String)

fun NavController.navigateToEditFineScreen (
    destination: EditFineDestination,
    navOptions: NavOptions? = null
){
    this.navigate(
        destination,
        navOptions
    )
}

fun NavGraphBuilder.editFineScreen(
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit
){
    composable<EditFineDestination> { backStackEntry ->
        val destination: EditFineDestination = backStackEntry.toRoute()
        FineSelectionRoute(
            accessKey = destination.accessKey,
            onBackClick = onBackClick,
            onConfirm = onConfirmClick
        )
    }
}