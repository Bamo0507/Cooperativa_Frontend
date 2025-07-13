package app.cooperativa.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import app.cooperativa.data.model.dto.Estados
import app.cooperativa.theme.CoopTheme

@Composable
fun getStatusColor(estado: Estados): Color {
    return when(estado){
        Estados.APROBADO -> CoopTheme.colorScheme.approved
        Estados.PENDIENTE -> CoopTheme.colorScheme.pending
        Estados.RECHAZADO -> CoopTheme.colorScheme.rejected
    }
}

fun getStatusText(estado: Estados): String{
    return when(estado){
        Estados.APROBADO -> "Aprobado"
        Estados.PENDIENTE -> "En Revisión"
        Estados.RECHAZADO -> "Rechazado"
    }
}
