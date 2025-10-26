package app.cooperativa.presentation.mainflow.socios

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
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.cooperativa.navigation.SociosBottomNavBar
import app.cooperativa.navigation.topLevelDestinationsSocios
import app.cooperativa.presentation.mainflow.socios.account.sAccountNavGraph
import app.cooperativa.presentation.mainflow.socios.historial.SHistorialNavGraph
import app.cooperativa.presentation.mainflow.socios.historial.mainHistorial.SHistorialDestination
import app.cooperativa.presentation.mainflow.socios.historial.sHistorialNavGraph
import app.cooperativa.presentation.mainflow.socios.pagos.sPagosNavGraph

@Composable
fun SociosMainFlowScreen(
    navController: NavHostController = rememberNavController(),
    onLogOutClick: () -> Unit,
    onChangeToDirectiva: () -> Unit
) {
    var bottomBarVisible by rememberSaveable {
        mutableStateOf(false)
    }

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    bottomBarVisible = if (currentDestination != null){
        topLevelDestinationsSocios.any { destination ->
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
                SociosBottomNavBar(
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
            startDestination = SHistorialNavGraph,
            modifier = Modifier.padding(padding)
        ){
            //Declare all the navigation graphs for Affiliates
            sHistorialNavGraph()

            sAccountNavGraph(
                onLogOutClick = onLogOutClick,
                onChangeToDirectiva = onChangeToDirectiva
            )

            sPagosNavGraph(
                navController = navController
            )
        }

    }



}