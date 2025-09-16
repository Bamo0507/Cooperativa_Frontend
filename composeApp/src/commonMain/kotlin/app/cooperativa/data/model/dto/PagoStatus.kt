package app.cooperativa.data.model.dto

data class PagosStatus(
    val pagoId: String,
    val nombrePago: String,
    val estado: Estados,
    val dateOfPayment: String
)