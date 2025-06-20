package app.cooperativa.data.model.dto

import kotlinx.datetime.LocalDate

data class Prestamo(
    val idPrestamo: Int,
    val nombreSolicitante: String,
    val nombre: String,
    val montoTotal: Float,
    val montoCancelado: Float,
    val motivo: String,
    val estado: Estados,
    val tasaInteres: Float,
    val fechaSolicitud: LocalDate,
    val plazoMeses: Int,
    val mesesCancelados: Int,
    val codeudores: List<Codeudor>,
    val mensualidadesPrestamo: List<PrestamoDetalle>,
)
