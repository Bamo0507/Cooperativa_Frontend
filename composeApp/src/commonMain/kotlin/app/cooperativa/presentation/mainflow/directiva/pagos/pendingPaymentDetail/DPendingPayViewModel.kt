package app.cooperativa.presentation.mainflow.directiva.pagos.pendingPaymentDetail

import app.cooperativa.domain.directiva.DPendingPayRepository
import androidx.lifecycle.ViewModel

class DPendingPayViewModel(
    private val repository: DPendingPayRepository,
    private val paymentId: Int
): ViewModel() {

}