package app.cooperativa.data.model.dto

import app.cooperativa.data.model.dto.Codeudor

data class SolicitudPrestamo(
    val nombreSolicitante: String,
    val nombrePrestamo: String,
    val montoTotal: Float,
    val plazoMeses: Int,
    val motivo: String,
    val codeudores: List<Codeudor>?,
    val comentarios: String?
)