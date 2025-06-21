package app.cooperativa.presentation.mainflow.socios.prestamos.loadPagare

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopButton
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopOutlinedButton
import app.cooperativa.theme.components.CoopOutlinedCard
import app.cooperativa.theme.components.CoopOutlinedTextField
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar
import app.cooperativa.utils.formatMoney

@Composable
fun SLoadPagareRoute(
    onBackClick: () -> Unit
){
    SLoadPagareScreen(
        onBackClick = onBackClick
    )
}

@Composable
fun SLoadPagareScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = {
            CoopTopBar(
                title = "Presentar Pagaré",
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