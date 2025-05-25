package app.cooperativa.presentation.mainflow.directiva.prestamos.loanRequestDetail

import app.cooperativa.data.model.dto.SolicitudPrestamo

data class SolicitudPrestamoState(
    val prestamo: SolicitudPrestamo? = null,
    val interestInput: Float = 0f,
    val commentsInput: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
