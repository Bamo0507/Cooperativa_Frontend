package app.cooperativa.presentation.mainflow.directiva

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.cooperativa.navigation.DirectivaBottomNavBar
import app.cooperativa.navigation.topLevelDestinationsDirectiva
import app.cooperativa.presentation.mainflow.directiva.pagos.DPaymentNavGraph
import app.cooperativa.presentation.mainflow.directiva.pagos.dPaymentNavGraph
import app.cooperativa.presentation.mainflow.directiva.prestamos.dLoanNavGraph

@Composable
fun DirectivaMainFlowScreen(
    navController: NavHostController = rememberNavController(),
    onLogOutClick: () -> Unit = {}, //TODO: IMPLEMENTAR
){
    var bottomBarVisible by rememberSaveable {
        mutableStateOf(false)
    }

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    bottomBarVisible = if (currentDestination != null){
        topLevelDestinationsDirectiva.any { destination ->
            currentDestination.hasRoute(destination)
        }
    } else {
        false
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = bottomBarVisible,
                enter = slideInVertically(initialOffsetY = {it}),
                exit = slideOutVertically(targetOffsetY = {it})
            ){
                DirectivaBottomNavBar(
                    checkItemSelected = { destination ->
                        currentDestination?.hierarchy?.any { it.hasRoute(destination::class) } ?: false
                    },
                    onNavItemClick = { destination ->
                        navController.navigate(destination) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

            }
        }
    ){ padding ->
        NavHost(
            navController = navController,
            startDestination = DPaymentNavGraph,
            modifier = Modifier.padding(padding)
        ){
            dPaymentNavGraph(navController)

            dLoanNavGraph(navController)



        }

    }

}