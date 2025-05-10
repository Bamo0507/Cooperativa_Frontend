package app.cooperativa.data.model.dto

import kotlinx.datetime.LocalDate

data class LoanPayment(
    val loanName: String,
    val userName: String,
    val amountPayed: Float,
    val numberOfPayment: Int,
    val totalLoanPayments: Int,
    val date: LocalDate
)