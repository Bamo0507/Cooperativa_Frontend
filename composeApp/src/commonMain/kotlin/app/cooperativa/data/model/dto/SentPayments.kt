package app.cooperativa.data.model.dto

data class SentPayments(
    val pagoId: Int,
    val nombrePago: String,
    val estado: Estados
)
