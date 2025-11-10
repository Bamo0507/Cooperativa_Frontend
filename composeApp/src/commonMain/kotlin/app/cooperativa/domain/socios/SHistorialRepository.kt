package app.cooperativa.domain.socios

import app.cooperativa.core.network.apollo.GraphQlException
import app.cooperativa.core.network.apollo.executeQuery
import app.cooperativa.data.model.dto.HistoryResponse
import app.cooperativa.data.model.dto.Loan
import app.cooperativa.data.model.dto.LoanUiStatus
import app.cooperativa.graphql.GetHistoryQuery
import app.cooperativa.graphql.GetUserLoansQuery
import com.apollographql.apollo3.ApolloClient

interface SHistorialRepository {
    suspend fun getPrestamosByUser(accessToken: String): List<Loan>
    suspend fun fetchHistory(accessToken: String): HistoryResponse
}

// ---------------------------------------------------------------------

class SociosHistorialRepository(
    private val apolloPayment: ApolloClient, // /graphql/payment (history)
    private val apolloLoan: ApolloClient // /graphql/loan (loans)
) : SHistorialRepository {

    override suspend fun getPrestamosByUser(accessToken: String): List<Loan> {
        val resp = apolloLoan.query(GetUserLoansQuery(accessToken = accessToken)).execute()

        if (resp.hasErrors()) {
            val msg = resp.errors?.joinToString { it.message }.orEmpty()
            throw GraphQlException(msg.ifBlank { "Error GraphQL (getUserLoans)" })
        }

        val items = resp.data?.getUserLoans.orEmpty()

        return items.map { l ->
            Loan(
                id = l.id,
                presentedByName = l.presentedByName,
                reason = l.reason,
                total = l.total.toFloat(),
                payed = l.payed.toFloat(),
                debt = l.debt.toFloat(),
                interestRate = l.interestRate.toFloat(),
                quotas = l.quotas,
                status = when (l.status) {
                    "OVERDUE" -> LoanUiStatus.OVERDUE
                    "ACTIVE" -> LoanUiStatus.ACTIVE
                    "PENDING"-> LoanUiStatus.PENDING
                    "PAYED" -> LoanUiStatus.PAYED
                    else -> LoanUiStatus.UNKNOWN
                }
            )
        }
    }

    override suspend fun fetchHistory(accessToken: String): HistoryResponse {
        return apolloPayment.executeQuery(GetHistoryQuery(accessToken = accessToken)) { data ->
            val h = data.getHistory
                ?: throw GraphQlException("Ooops, no se pudo obtener el historial")

            HistoryResponse(
                owedCapital = h.owedCapital?.toFloat() ?: 0f,
                payedToCapital = h.payedToCapital?.toFloat() ?: 0f
            )
        }
    }
}