package app.cooperativa.data.model.dto

data class Pagare(
    val pagare: String, //llave para acceder
    val estado: Estados,
    val comentariosRechazo: String?
)
