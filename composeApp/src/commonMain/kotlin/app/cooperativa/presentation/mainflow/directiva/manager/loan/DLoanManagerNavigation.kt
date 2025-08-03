package app.cooperativa.presentation.mainflow.directiva.manager.loan

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object DLoanManagerDestination

fun NavGraphBuilder.loanForm(
    onBackClick: () -> Unit
){
    composable<DLoanManagerDestination> {
        DLoanManagerRoute(
            onBackClick = onBackClick
        )
    }
}