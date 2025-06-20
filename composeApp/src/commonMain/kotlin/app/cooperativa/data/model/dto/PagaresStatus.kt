package app.cooperativa.data.model.dto

data class PagaresStatus(
    val solicitudId: Int,
    val solicitanteId: Int,
    val prestamoNombre: String,
    val estadoPagare: Estados = Estados.PENDIENTE
)

