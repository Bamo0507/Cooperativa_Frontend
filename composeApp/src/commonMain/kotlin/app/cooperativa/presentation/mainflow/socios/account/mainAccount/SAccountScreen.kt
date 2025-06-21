package app.cooperativa.presentation.mainflow.socios.account.mainAccount

import androidx.compose.runtime.Composable

@Composable
fun SAccountRoute(
    onLogOutClick: () -> Unit,
    onChangeToDirectiva: () -> Unit
){

    SAccountScreen(
        onLogOutClick = onLogOutClick,
        onChangeToDirectiva = onChangeToDirectiva
    )
}

@Composable
fun SAccountScreen(
    onLogOutClick: () -> Unit,
    onChangeToDirectiva: () -> Unit
){

}