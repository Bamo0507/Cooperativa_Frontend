package app.cooperativa.presentation.mainflow.socios.pagos.pagoStatus

import app.cooperativa.data.model.dto.PagosStatus

data class SPagosStatusState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val pagosStatus: List<PagosStatus> = emptyList()
)
