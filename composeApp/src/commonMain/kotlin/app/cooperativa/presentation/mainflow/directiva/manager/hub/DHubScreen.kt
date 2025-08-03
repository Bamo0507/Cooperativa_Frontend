package app.cooperativa.presentation.mainflow.directiva.manager.hub

import androidx.compose.runtime.Composable

@Composable
fun DHubRoute(
    onFineClick: () -> Unit,
    onLoanClick: () -> Unit
){
    DHubScreen(
        onFineClick = onFineClick,
        onLoanClick = onLoanClick
    )
}

@Composable
fun DHubScreen(
    onFineClick: () -> Unit,
    onLoanClick: () -> Unit
) {

}