package app.cooperativa.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import app.cooperativa.data.model.dto.Estados
import app.cooperativa.theme.CoopTheme

@Composable
fun getStatusColor(estado: Estados): Color {
    return when(estado){
        Estados.ACCEPTED -> CoopTheme.colorScheme.approved
        Estados.ON_REVISION -> CoopTheme.colorScheme.pending
        Estados.REJECTED -> CoopTheme.colorScheme.rejected
    }
}

fun getStatusText(estado: Estados): String{
    return when(estado){
        Estados.ACCEPTED -> "Aprobado"
        Estados.ON_REVISION -> "En Revisión"
        Estados.REJECTED -> "Rechazado"
    }
}
