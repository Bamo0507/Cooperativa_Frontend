package app.cooperativa.data.model.dto

data class PagosStatus(
    val pagoId: Int,
    val nombrePago: String,
    val estado: Estados
)