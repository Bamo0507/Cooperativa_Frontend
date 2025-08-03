package app.cooperativa.presentation.mainflow.directiva.manager.fine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
fun DFineManagerRoute(
    onBackClick: () -> Unit
){

    DFineManagerScreen(
        onBackClick = onBackClick
    )
}

@Composable
fun DFineManagerScreen(
    onBackClick: () -> Unit
){
    Scaffold(
        topBar = {
            CoopTopBar(
                title = "Multa",
                leadingArrow = true,
                onBackClick = onBackClick,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        },
        containerColor = CoopTheme.colorScheme.surface
    ){ padding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .background(CoopTheme.colorScheme.surface)
                .padding(padding)
                .padding(vertical = 6.dp, horizontal = 8.dp)
        ){



        }

    }

}