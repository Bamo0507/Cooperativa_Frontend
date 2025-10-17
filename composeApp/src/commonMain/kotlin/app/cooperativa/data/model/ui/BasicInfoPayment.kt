package app.cooperativa.data.model.ui

data class BasicInfoPayment(
    val id: String,
    val paymentName: String,
    val username: String,
    val dateOfPayment: String,
    val isPaymentPending: Boolean = true
)
