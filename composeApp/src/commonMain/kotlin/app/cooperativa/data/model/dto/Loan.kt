package app.cooperativa.data.model.dto

enum class LoanUiStatus { OVERDUE, ACTIVE, PENDING, PAYED, UNKNOWN }

data class Loan(
    val id: String,
    val presentedByName: String,
    val reason: String,
    val total: Float,
    val payed: Float,
    val debt: Float,
    val interestRate: Float,
    val quotas: Int,
    val status: LoanUiStatus
)