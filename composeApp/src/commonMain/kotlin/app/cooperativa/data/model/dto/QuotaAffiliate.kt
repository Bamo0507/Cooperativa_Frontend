package app.cooperativa.data.model.dto

data class QuotaAffiliate(
    val idCuota: Int,
    val idAsociado: Int,
    val nombreAsociado: String,
    val montoCuota: Float,
)
