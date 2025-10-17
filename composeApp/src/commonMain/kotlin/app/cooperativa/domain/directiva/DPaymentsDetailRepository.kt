package app.cooperativa.domain.directiva

import app.cooperativa.data.localdb.directiva.PaymentMockData
import app.cooperativa.data.model.dto.Payment
import app.cooperativa.graphql.GetAllPaymentsQuery
import kotlinx.datetime.LocalDate

interface DPaymentsDetailRepository {
    suspend fun getPaymentById(id: String): Payment
}

class DirectivePaymentsDetailRepository(
    private val apollo: com.apollographql.apollo3.ApolloClient
) : DPaymentsDetailRepository {

    override suspend fun getPaymentById(id: String): Payment {
        val response = apollo.query(GetAllPaymentsQuery()).execute()

        if (response.hasErrors()) {
            val msg = response.errors?.joinToString { it.message }.orEmpty()
            throw RuntimeException(msg.ifBlank { "Error GraphQL (getAllPayments)" })
        }

        val list = response.data?.getAllPayments.orEmpty()
        val node = list.firstOrNull { it.id == id }
            ?: throw NoSuchElementException("Pago no encontrado (id=$id)")

        return node.toDomainPayment()
    }
}
