package app.cooperativa.domain.directiva

import app.cooperativa.data.localdb.directiva.FineMockData
import app.cooperativa.data.localdb.directiva.PaymentMockData
import app.cooperativa.data.model.dto.Fine
import app.cooperativa.data.model.ui.BasicInfoPayment
import app.cooperativa.graphql.GetAllPaymentsQuery
import app.cooperativa.graphql.type.PaymentStatus
import com.apollographql.apollo3.ApolloClient

interface DPaymentsRepository {
    suspend fun getAllPaymentsBasicInfo(): List<BasicInfoPayment>
    suspend fun getAllFines(): List<Fine>
}

class DirectivePaymentsRepository(
    private val apollo: ApolloClient  // graphql/payment
) : DPaymentsRepository {

    override suspend fun getAllPaymentsBasicInfo(): List<BasicInfoPayment> {
        val resp = apollo.query(GetAllPaymentsQuery()).execute()

        if (resp.hasErrors()) {
            val msg = resp.errors?.joinToString { it.message }.orEmpty()
            throw RuntimeException(msg.ifBlank { "Error GraphQL (getAllPayments)" })
        }

        val items = resp.data?.getAllPayments.orEmpty()

        return items
            // Excluir REJECTED de la lista
            .filter { it.state == PaymentStatus.ON_REVISION || it.state == PaymentStatus.ACCEPTED }
            .map { p ->
                BasicInfoPayment(
                    id = p.id,
                    paymentName = p.name,
                    username = "User - ${p.totalAmount}",
                    dateOfPayment = p.paymentDate,
                    isPaymentPending = (p.state == PaymentStatus.ON_REVISION)
                )
            }
    }

    // Sigue mock hasta que exista query real:
    override suspend fun getAllFines(): List<Fine> = FineMockData.getAllFines()
}