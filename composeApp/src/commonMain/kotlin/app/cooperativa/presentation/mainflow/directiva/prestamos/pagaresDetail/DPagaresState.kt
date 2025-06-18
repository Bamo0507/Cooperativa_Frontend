package app.cooperativa.presentation.mainflow.directiva.prestamos.pagaresDetail

import app.cooperativa.data.model.dto.Pagare

data class DPagaresState(
    val pagare: Pagare? = null,
    val commentsInput: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
