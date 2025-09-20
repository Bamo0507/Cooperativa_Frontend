package app.cooperativa.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import app.cooperativa.data.model.dto.Estados
import app.cooperativa.theme.CoopTheme

@Composable
fun getStatusColor(estado: Estados): Color {
    return when(estado){
        Estados.COMPLETED -> CoopTheme.colorScheme.approved
        Estados.PENDING -> CoopTheme.colorScheme.pending
        Estados.ON_REVISION -> CoopTheme.colorScheme.rejected
    }
}

fun getStatusText(estado: Estados): String{
    return when(estado){
        Estados.COMPLETED -> "Aprobado"
        Estados.PENDING -> "En Revisión"
        Estados.ON_REVISION -> "Rechazado"
    }
}
