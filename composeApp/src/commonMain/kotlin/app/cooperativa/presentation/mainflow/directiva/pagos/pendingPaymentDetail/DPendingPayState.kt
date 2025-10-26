package app.cooperativa.presentation.mainflow.directiva.pagos.pendingPaymentDetail

import app.cooperativa.data.model.dto.Payment

data class DPendingPayState(
    val payment: Payment? = null,
    val commentInput: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showRejectDialog: Boolean = false,
    val navigateBack: Boolean = false
)
