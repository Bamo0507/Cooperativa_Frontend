package app.cooperativa.presentation.mainflow.directiva.pagos

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import app.cooperativa.navigation.utils.NavResultKeys
import app.cooperativa.presentation.mainflow.directiva.pagos.fineSelection.EditFineDestination
import app.cooperativa.presentation.mainflow.directiva.pagos.fineSelection.editFineScreen
import app.cooperativa.presentation.mainflow.directiva.pagos.fineSelection.navigateToEditFineScreen
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
            },

            // For now, key is hard coded, will be replaced
            // once query to retrieve all fines is created
            onFineClick = {
                navController.navigateToEditFineScreen(
                    destination = EditFineDestination(
                        accessKey = "77656D82A042ABA5AE02293A880479D3DACA6609331486E01F351285990F6235"
                    )
                )
            }
        )

        //Pantalla de detalle de pago pendiente
        pendingPaymentScreen(
            onBackClick = {
                navController.navigateUp()
            },
            onBackWithConfettiClick = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(NavResultKeys.CONFETTI, true)

                navController.navigateUp()
            }
        )

        //Pantalla de detalle de pago realizado
        paidPaymentScreen(
            onBackClick = {
                navController.navigateUp()
            }
        )

        //Pantalla de edicion de mora
        editFineScreen(
            onBackClick = {
                navController.navigateUp()
            },
            onConfirmClick = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(NavResultKeys.CONFETTI, true)

                navController.navigateUp()
            }
        )
    }
}