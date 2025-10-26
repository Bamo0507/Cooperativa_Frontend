package app.cooperativa.data.model.dto

import kotlinx.datetime.LocalDate

data class Payment(
    val id: String,

    val paymentName: String,
    val userName: String,
    val paymentDate: LocalDate,
    val dateOfPayment: String,

    val totalAmount: Float,

    val quotas: List<Quotas>? = null,
    val loanPayments: List<LoanPayment>? = null,
    val finePayments: List<FinePayment>? = null,
    val contributionPayments: List<Contribution>? = null,

    val paymentImage: String,

    val isPaymentPending: Boolean = true,

    val accountNumber: String? = null,
    val receiptNumber: String? = null
)
