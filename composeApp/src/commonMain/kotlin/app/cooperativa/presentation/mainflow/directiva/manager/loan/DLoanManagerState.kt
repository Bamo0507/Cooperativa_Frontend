package app.cooperativa.presentation.mainflow.directiva.manager.loan

import app.cooperativa.data.model.dto.Member

data class DLoanManagerState(
    val memberOptions: List<Member> = emptyList(),

    val affiliateName: String = "",
    val affiliateId: String = "",

    val loanReason: String = "",

    val amount: Float = 0.0f,
    val amountText: String = "",

    val interest: Float = 12.0f,
    val interestText: String = "12.00",

    val isLoading: Boolean = false,
    val errorMessage: String? = null
)