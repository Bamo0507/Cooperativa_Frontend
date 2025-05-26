package app.cooperativa.presentation.mainflow.directiva.pagos

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral.GeneralPaymentDestination
import app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral.boardGeneralPayment
import app.cooperativa.presentation.mainflow.directiva.pagos.paymentDetail.DPaidPayDestination
import app.cooperativa.presentation.mainflow.directiva.pagos.paymentDetail.navigateToDPaidPayScreen
import app.cooperativa.presentation.mainflow.directiva.pagos.paymentDetail.paidPaymentScreen
import app.cooperativa.presentation.mainflow.directiva.pagos.pendingPaymentDetail.PendingPaymentDestination
import app.cooperativa.presentation.mainflow.directiva.pagos.pendingPaymentDetail.navigateToPendingPaymentScreen
import app.cooperativa.presentation.mainflow.directiva.pagos.pendingPaymentDetail.pendingPaymentScreen
import kotlinx.serialization.Serializable

@Serializable
data object DPaymentNavGraph

fun NavGraphBuilder.dPaymentNavGraph(
    navController: NavController
){
    navigation<DPaymentNavGraph>(startDestination = GeneralPaymentDestination){
        //Pantalla General de los pagos
        boardGeneralPayment(
            onPendingPaymentClick = { payment ->
                navController.navigateToPendingPaymentScreen(
                    destination = PendingPaymentDestination(
                        paymentId = payment
                    )
                )
            },

            onPaidPaymentClick = { payment ->
                navController.navigateToDPaidPayScreen(
                    destination = DPaidPayDestination(
                        paymentId = payment
                    )
                )
            }
        )

        //Pantalla de detalle de pago pendiente
        pendingPaymentScreen(
            onBackClick = {
                navController.navigateUp()
            }
        )

        //Pantalla de detalle de pago realizado
        paidPaymentScreen(
            onBackClick = {
                navController.navigateUp()
            }
        )
    }
}