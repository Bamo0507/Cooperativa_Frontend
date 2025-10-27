package app.cooperativa.presentation.mainflow.directiva.manager

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import app.cooperativa.navigation.utils.NavResultKeys
import app.cooperativa.presentation.mainflow.directiva.manager.fine.DFineManagerDestination
import app.cooperativa.presentation.mainflow.directiva.manager.fine.fineForm
import app.cooperativa.presentation.mainflow.directiva.manager.hub.DHubDestination
import app.cooperativa.presentation.mainflow.directiva.manager.hub.hubManager
import app.cooperativa.presentation.mainflow.directiva.manager.loan.DLoanManagerDestination
import app.cooperativa.presentation.mainflow.directiva.manager.loan.loanForm
import kotlinx.serialization.Serializable

@Serializable
data object DManagerNavGraph

fun NavGraphBuilder.dManagerNavGraph(
    navController: NavController
){
    navigation<DManagerNavGraph>(startDestination = DHubDestination){
        hubManager(
            onLoanClick = {
                navController.navigate(DLoanManagerDestination)
            },
            onFineClick = {
                navController.navigate(DFineManagerDestination)
            }
        )

        fineForm(
            onBackClick = {
                navController.navigateUp()
            },
            onBackWithConfettiClick = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(NavResultKeys.CONFETTI, true)

                navController.navigateUp()
            }
        )

        loanForm(
            onBackClick = {
                navController.navigateUp()
            }
        )
    }
}