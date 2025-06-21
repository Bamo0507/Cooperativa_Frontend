package app.cooperativa.presentation.mainflow.socios.prestamos.solicitudPrestamo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopTopBar

@Composable
fun SolicitudPrestamoRoute(
    onBackClick: () -> Unit
){
    SolicitudPrestamoScreen(
        onBackClick = onBackClick
    )
}

@Composable
fun SolicitudPrestamoScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = {
            CoopTopBar(
                title = "Solicitud de Préstamo",
                leadingArrow = true,
                onBackClick = onBackClick,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        },
        containerColor = CoopTheme.colorScheme.surface
    ) { padding ->
        Column(
            modifier = modifier
                .background(CoopTheme.colorScheme.surface)
                .padding(padding)
                .padding(vertical = 6.dp, horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {

        }
    }

}