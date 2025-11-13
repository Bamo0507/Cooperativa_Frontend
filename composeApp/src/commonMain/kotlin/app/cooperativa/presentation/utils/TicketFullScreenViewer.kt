package app.cooperativa.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.cooperativa.theme.CoopTheme
import coil3.compose.AsyncImage

@Composable
fun TicketFullScreenViewer(
    model: Any?,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 300.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(CoopTheme.colorScheme.surface)
        ) {
            AsyncImage(
                model = model,
                contentDescription = "Boleta",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 300.dp),
                contentScale = ContentScale.Fit
            )
            // Cerrar (arriba a la derecha)
            IconButton(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cerrar",
                    tint = CoopTheme.colorScheme.onSurface
                )
            }
        }
    }
}