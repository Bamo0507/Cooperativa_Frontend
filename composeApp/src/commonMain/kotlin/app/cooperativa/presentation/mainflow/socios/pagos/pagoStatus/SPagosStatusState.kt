package app.cooperativa.presentation.mainflow.socios.pagos.pagoStatus

import app.cooperativa.data.model.dto.SentPayments

data class SPagosStatusState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val pagosStatus: List<SentPayments>
)
