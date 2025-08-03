package app.cooperativa.presentation.mainflow.directiva.manager.fine

import app.cooperativa.domain.directiva.Member

data class DFineManagerState(
    val memberOptions: List<Member> = emptyList(),

    val fineName: String = "",
    val fineAmount: Float = 0.0f,
    val affiliateName: String = "",
    val affiliateId: Int = 0,

    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
