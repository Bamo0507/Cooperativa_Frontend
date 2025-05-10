package app.cooperativa.data.model.dto

import kotlinx.datetime.LocalDate

data class Payment(
    val id: Int,

    val paymentName: String,
    val userName: String,
    val paymentDate: LocalDate,

    val quotas: List<Quotas>? = null,
    val loanPayments: List<LoanPayment>? = null,

    val paymentImage: String,

    val isPaymentPending: Boolean = true
)
