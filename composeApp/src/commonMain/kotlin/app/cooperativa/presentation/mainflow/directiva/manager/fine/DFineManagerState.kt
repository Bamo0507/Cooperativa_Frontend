package app.cooperativa.presentation.mainflow.directiva.manager.fine

data class DFineManagerState(
    val fineName: String = "",
    val fineAmount: Float = 0.0f,
    val affiliateName: String = "",
    val affiliateId: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
