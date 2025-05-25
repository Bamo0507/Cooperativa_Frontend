package app.cooperativa.presentation.mainflow.directiva.pagos.paymentDetail

import app.cooperativa.data.model.dto.Payment

data class DPaidPayState(
    val payment: Payment? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
