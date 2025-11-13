package app.cooperativa.data.model.dto

import app.cooperativa.graphql.type.PaymentStatus
import kotlinx.datetime.LocalDate

data class Payment(
    val id: String,
    val name: String,
    val presentedByName: String,
    val commentary: String?,
    val paymentDate: String,
    val state: PaymentStatus,
    val ticketNum: String,
    val photoPath: String,
    val totalAmount: Float,
    val accountNum: String,
    val beingPayed: List<PayedToInput> = emptyList()
)