package app.cooperativa.presentation.mainflow.directiva.prestamos.prestamosGeneral

import app.cooperativa.data.model.dto.Prestamo
import app.cooperativa.data.model.ui.BasicInfoLoan

data class DPrestamoState(
    val selectedTabIndex: Int = 0,
    val searchQuery: String = "",
    val reqLoans: List<BasicInfoLoan> = emptyList(),
    val allLoans: List<Prestamo> = emptyList(),
    val prestamosVigentes: List<Prestamo> = emptyList(),
    val prestamosCompletados: List<Prestamo> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
