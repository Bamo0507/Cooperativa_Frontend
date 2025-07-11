package app.cooperativa.presentation.mainflow.socios.pagos.agregarPago

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopTopBar

@Composable
fun SPagoEnviarRoute(
    onBackClick: () -> Unit
){
    SPagoEnviarScreen(
        onBackClick = onBackClick
    )
}

@Composable
fun SPagoEnviarScreen(
    onBackClick: () -> Unit,
    modfiier: Modifier = Modifier
){
    Scaffold(
        topBar = {
            CoopTopBar(
                title = "Presentar Pago",
                leadingArrow = true,
                onBackClick = { onBackClick() },
            )
        },
        containerColor = CoopTheme.colorScheme.surface
    ){ padding ->
        LazyColumn(
            modifier = modfiier.padding(padding)
        ) {
            

        }
    }

}