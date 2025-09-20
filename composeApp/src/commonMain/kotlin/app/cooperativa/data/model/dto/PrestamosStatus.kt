package app.cooperativa.data.model.dto

data class PrestamosStatus(
    val solicitudId: Int,
    val solicitanteId: Int,
    val prestamoNombre: String,
    val estadoPrestamo: Estados = Estados.PENDING,
    val linkDescargaPagare: String
)
