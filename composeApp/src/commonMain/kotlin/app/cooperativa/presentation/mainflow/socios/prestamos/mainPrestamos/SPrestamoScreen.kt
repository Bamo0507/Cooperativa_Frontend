package app.cooperativa.presentation.mainflow.socios.prestamos.mainPrestamos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopText

@Composable
fun SPrestamoRoute(){
    SPrestamoScreen()
}

@Composable
fun SPrestamoScreen(){
    Column(modifier = Modifier.fillMaxSize().background(CoopTheme.colorScheme.primary)){
        CoopText("PRESTAMOS")
    }
}