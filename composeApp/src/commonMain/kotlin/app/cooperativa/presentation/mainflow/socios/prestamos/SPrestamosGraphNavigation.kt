package app.cooperativa.presentation.mainflow.socios.prestamos

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import app.cooperativa.presentation.mainflow.socios.prestamos.mainPrestamos.SPrestamoDestination
import app.cooperativa.presentation.mainflow.socios.prestamos.mainPrestamos.sociosPrestamosScreen
import kotlinx.serialization.Serializable

@Serializable
data object SPrestamoNavGraph

fun NavGraphBuilder.sPrestamosNavGraph(){
    navigation<SPrestamoNavGraph>(startDestination = SPrestamoDestination){
        sociosPrestamosScreen()


    }
}