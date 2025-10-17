package app.cooperativa.domain.directiva

import app.cooperativa.data.localdb.directiva.PaymentMockData
import app.cooperativa.data.model.dto.Payment
import app.cooperativa.graphql.ApproveOrRejectPaymentMutation
import app.cooperativa.graphql.GetAllPaymentsQuery
import kotlinx.datetime.LocalDate

interface DPendingPayRepository {
    suspend fun getPaymentById(id: String): Payment?
    suspend fun approveOrRejectPayment(id: String, newState: String, commentary: String): Payment
}

class DirectivePendingPayRepository(
    private val apollo: com.apollographql.apollo3.ApolloClient
) : DPendingPayRepository {

    override suspend fun getPaymentById(id: String): Payment? {
        val resp = apollo.query(app.cooperativa.graphql.GetAllPaymentsQuery()).execute()

        if (resp.hasErrors()) {
            val msg = resp.errors?.joinToString { it.message }.orEmpty()
            throw RuntimeException(msg.ifBlank { "Error GraphQL (getAllPayments)" })
        }

        val node = resp.data?.getAllPayments?.firstOrNull { it.id == id } ?: return null
        return node.toDomainPayment()
    }

    override suspend fun approveOrRejectPayment(
        id: String,
        newState: String,
        commentary: String
    ): Payment {
        val resp = apollo.mutation(
            app.cooperativa.graphql.ApproveOrRejectPaymentMutation(
                id = id,
                newState = newState,
                commentary = commentary
            )
        ).execute()

        if (resp.hasErrors()) {
            val msg = resp.errors?.joinToString { it.message }.orEmpty()
            throw RuntimeException(msg.ifBlank { "Error GraphQL (approveOrRejectPayment)" })
        }

        val node = resp.data?.approveOrRejectPayment
            ?: throw RuntimeException("Respuesta vacía (approveOrRejectPayment)")

        return node.toDomainPayment()
    }
}

// temporary mappers, will be deleted once backend adds logic
fun GetAllPaymentsQuery.GetAllPayment.toDomainPayment(): Payment {
    val localDate = LocalDate.parse(paymentDate) // "YYYY-MM-DD"
    return Payment(
        id = id,
        paymentName = name,
        userName = "User - $totalAmount",
        paymentDate = localDate,
        dateOfPayment = paymentDate,
        quotas = null,
        loanPayments = null,
        finePayments = null,
        contributionPayments = null,
        paymentImage = photo,
        isPaymentPending = (state.rawValue == "ON_REVISION"),
        accountNumber = accountNum,
        receiptNumber = ticketNum,
        totalAmount = totalAmount.toFloat()
    )
}

fun ApproveOrRejectPaymentMutation.ApproveOrRejectPayment.toDomainPayment(): Payment {
    val localDate = LocalDate.parse(paymentDate) // "YYYY-MM-DD"
    return Payment(
        id = id,
        paymentName = name,
        userName = "User - $totalAmount",
        paymentDate = localDate,
        dateOfPayment = paymentDate,
        quotas = null,
        loanPayments = null,
        finePayments = null,
        contributionPayments = null,
        paymentImage = photo,
        isPaymentPending = (state.rawValue == "ON_REVISION"),
        accountNumber = accountNum,
        receiptNumber = ticketNum,
        totalAmount = totalAmount.toFloat()
    )
}