package app.cooperativa.data.model.dto

import kotlinx.datetime.LocalDate

data class PrestamoDetalle(
    val numeroCuota: Int,
    val montoCuota: Float,
    val fechaVencimiento: LocalDate,
    val montoPagado: Float,
    val multa: Float
)
