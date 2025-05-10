package app.cooperativa.data.model.ui

data class BasicInfoPayment(
    val id: Int,
    val paymentName: String,
    val username: String,
    val isPaymentPending: Boolean = true
)
