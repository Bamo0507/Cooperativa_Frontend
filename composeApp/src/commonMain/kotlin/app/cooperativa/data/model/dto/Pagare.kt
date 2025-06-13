package app.cooperativa.data.model.dto

data class Pagare(
    val idPagare: Int,
    val solicitante: String,
    val nombrePrestamo: String,
    val pagareKey: String, //llave para acceder
    val estado: Estados,
    val comentariosRechazo: String?
)
