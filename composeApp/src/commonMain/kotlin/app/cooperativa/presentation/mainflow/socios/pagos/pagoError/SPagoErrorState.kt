package app.cooperativa.presentation.mainflow.socios.pagos.pagoError

data class SPagoErrorState(
    val directiveMessage: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
