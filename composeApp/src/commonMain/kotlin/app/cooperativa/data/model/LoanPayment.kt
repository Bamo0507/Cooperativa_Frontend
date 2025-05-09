package app.cooperativa.data.model

data class LoanPayment(
    val loanName: String,
    val userName: String,
    val amountPayed: Float,
    val numberOfPayment: Int,
    val totalLoanPayments: Int,
)