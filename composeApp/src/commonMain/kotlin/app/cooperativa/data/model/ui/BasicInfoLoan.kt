package app.cooperativa.data.model.ui

data class BasicInfoLoan(
    val id: Int,
    val loanName: String,
    val username: String,
    val isRevisionPending: Boolean = true
)
