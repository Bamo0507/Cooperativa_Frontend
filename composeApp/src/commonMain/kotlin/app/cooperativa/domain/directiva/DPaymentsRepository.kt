package app.cooperativa.domain.directiva

import app.cooperativa.data.localdb.directiva.FineMockData
import app.cooperativa.data.localdb.directiva.PaymentMockData
import app.cooperativa.data.model.dto.Fine
import app.cooperativa.data.model.dto.FineDetail
import app.cooperativa.data.model.ui.BasicInfoPayment
import app.cooperativa.graphql.EditFineMutation
import app.cooperativa.graphql.GetAllPaymentsQuery
import app.cooperativa.graphql.GetFinesQuery
import app.cooperativa.graphql.type.FineStatus
import app.cooperativa.graphql.type.PaymentStatus
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import kotlinx.datetime.LocalDate

interface DPaymentsRepository {
    suspend fun getAllPaymentsBasicInfo(): List<BasicInfoPayment>
    suspend fun getAllFines(): List<Fine>
    suspend fun editFine(
        fineId: String,
        newAmount: Float? = null,
        newMotive: String? = null,
        newStatus: FineStatus
    ): Unit
}

class DirectivePaymentsRepository(
    private val paymentApollo: ApolloClient,  // graphql/payment
    private val fineApollo: ApolloClient
) : DPaymentsRepository {

    override suspend fun getAllPaymentsBasicInfo(): List<BasicInfoPayment> {
        val resp = paymentApollo.query(GetAllPaymentsQuery()).execute()

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

    override suspend fun getAllFines(): List<Fine> {
        val resp = fineApollo.query(GetFinesQuery()).execute()

        if (resp.hasErrors()) {
            val msg = resp.errors?.joinToString { it.message }.orEmpty()
            throw RuntimeException(msg.ifBlank { "Error GraphQL (getFines)" })
        }

        val users = resp.data?.getFines.orEmpty()

        return users.map { u ->
            val details = u.fines
                .filter { it.status == FineStatus.UNPAID }   // Solo PAID
                .map { f ->
                    FineDetail(
                        id = f.id,
                        name = f.reason,
                        amount = f.amount.toFloat(),
                    )
                }

            Fine(
                userId = u.userId,
                userName = u.completeName,
                fineDetails = details
            )
        }.filter { it.fineDetails.isNotEmpty() }
    }

    override suspend fun editFine(
        fineId: String,
        newAmount: Float?,
        newMotive: String?,
        newStatus: FineStatus
    ) {
        val resp = fineApollo.mutation(
            EditFineMutation(
                fineKey = fineId,
                newAmount = newAmount?.let { Optional.Present(it.toDouble()) } ?: Optional.Absent,
                newMotive = newMotive?.let { Optional.Present(it) } ?: Optional.Absent,
                newStatus = newStatus?.let { Optional.Present(it) } ?: Optional.Absent
            )
        ).execute()

        if (resp.hasErrors()) {
            val msg = resp.errors?.joinToString { it.message }.orEmpty()
            throw RuntimeException(msg.ifBlank { "Error GraphQL (editFine)" })
        }
    }
}