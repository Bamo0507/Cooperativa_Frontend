package app.cooperativa.presentation.mainflow.socios.prestamos.mainPrestamos

import app.cooperativa.data.model.dto.PagaresStatus
import app.cooperativa.data.model.dto.PrestamosStatus

data class SPrestamoState(
    val prestamos: List<PrestamosStatus> = emptyList(),
    val pagares: List<PagaresStatus> = emptyList(),
    val selectedTabIndex: Int = 0,

    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
