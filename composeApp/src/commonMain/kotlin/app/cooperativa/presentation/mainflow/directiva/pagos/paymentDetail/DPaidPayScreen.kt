package app.cooperativa.presentation.mainflow.directiva.pagos.paymentDetail

import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun DPaidPayRoute(
    paymentId: Int,
    onBackClick: () -> Unit,
    viewModel: DPaidPayViewModel = koinInject { parametersOf(paymentId) }
){

}