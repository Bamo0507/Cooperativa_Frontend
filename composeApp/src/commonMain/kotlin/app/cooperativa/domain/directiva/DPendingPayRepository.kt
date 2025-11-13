package app.cooperativa.domain.directiva

import app.cooperativa.data.model.dto.PayedToEntry
import app.cooperativa.data.model.dto.Payment
import app.cooperativa.graphql.ApproveOrRejectPaymentMutation
import app.cooperativa.graphql.GetAllPaymentsQuery
import app.cooperativa.graphql.type.PaymentStatus
import com.apollographql.apollo3.ApolloClient
import kotlinx.datetime.LocalDate

interface DPendingPayRepository {
    suspend fun getPaymentById(id: String): Payment?
    suspend fun approveOrRejectPayment(id: String, newState: String, commentary: String): Payment
}

class DirectivePendingPayRepository(
    private val apollo: ApolloClient
) : DPendingPayRepository {

    override suspend fun getPaymentById(id: String): Payment? {
        val resp = apollo.query(GetAllPaymentsQuery()).execute()

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
            ApproveOrRejectPaymentMutation(
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

/** --- Mappers --- */
private fun safeParseLocalDateOrNull(yyyyMmDd: String): LocalDate? =
    try { LocalDate.parse(yyyyMmDd) } catch (_: Exception) { null }

// Si tu modelo de dominio usa SU PROPIO enum PaymentStatus:
private fun PaymentStatus.toDomain(): PaymentStatus =
    when (this) {
        PaymentStatus.ON_REVISION -> PaymentStatus.ON_REVISION
        PaymentStatus.REJECTED    -> PaymentStatus.REJECTED
        PaymentStatus.ACCEPTED    -> PaymentStatus.ACCEPTED
        else -> PaymentStatus.ON_REVISION
    }

// ------------------------- GetAllPayments -------------------------
fun GetAllPaymentsQuery.GetAllPayment.toDomainPayment(): Payment {
    return Payment(
        id = id,
        name = name,
        presentedByName = presentedByName,
        paymentDate = paymentDate,              // String ISO del backend
        photoPath = photoPath,
        state = state.toDomain(),               // o state.rawValue si usas String
        accountNum = accountNum,
        ticketNum = ticketNum,
        totalAmount = totalAmount.toFloat(),
        commentary = commentary,
        beingPayed = beingPayed.map { it.toDomain() }
    )
}

private fun GetAllPaymentsQuery.BeingPayed.toDomain(): PayedToEntry =
    PayedToEntry(
        modelKey = modelKey,
        modelType = modelType,
        amount = amount.toFloat()               // Apollo expone Float como Double
    )

// ---------- ApproveOrRejectPayment ----------
fun ApproveOrRejectPaymentMutation.ApproveOrRejectPayment.toDomainPayment(): Payment {
    return Payment(
        id = id,
        name = name,
        presentedByName = presentedByName,
        paymentDate = paymentDate,              // String
        photoPath = photoPath,
        state = state.toDomain(),               // o state.rawValue
        accountNum = accountNum,
        ticketNum = ticketNum,
        totalAmount = totalAmount.toFloat(),
        commentary = commentary,
        beingPayed = beingPayed.map { it.toDomain() }
    )
}

private fun ApproveOrRejectPaymentMutation.BeingPayed.toDomain(): PayedToEntry =
    PayedToEntry(
        modelKey = modelKey,
        modelType = modelType,
        amount = amount.toFloat()
    )