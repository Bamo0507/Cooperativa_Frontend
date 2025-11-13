package app.cooperativa.domain.directiva

import app.cooperativa.core.network.apollo.executeQuery
import app.cooperativa.data.model.dto.Member
import app.cooperativa.graphql.CreateUserLoanMutation
import app.cooperativa.graphql.GettingAffiliatesQuery
import com.apollographql.apollo3.ApolloClient


interface DLoanManagerRepository {
    suspend fun getAllAffiliates(): List<Member>

    suspend fun submitLoan(
        affiliateKey: String,
        totalQuota: Int,
        baseNeededPayment: Float,
        interestRate: Float,
        reason: String
    ): String
}

class DirectiveLoanManagerRepository(
    private val apolloPayment: ApolloClient,
    private val apolloLoan: ApolloClient
) : DLoanManagerRepository {

    override suspend fun getAllAffiliates(): List<Member> {
        return apolloPayment.executeQuery(GettingAffiliatesQuery()) { data ->
            data.getAllMembers.map { node ->
                Member(
                    userId = node.userId,
                    name = node.name
                )
            }
        }
    }

    override suspend fun submitLoan(
        affiliateKey: String,
        totalQuota: Int,
        baseNeededPayment: Float,
        interestRate: Float,
        reason: String
    ): String {
        val resp = apolloLoan.mutation(
            CreateUserLoanMutation(
                affiliateKey = affiliateKey,
                totalQuota = totalQuota,
                baseNeededPayment = baseNeededPayment.toDouble(),
                interestRate = interestRate.toDouble(),
                reason = reason
            )
        ).execute()

        if (resp.hasErrors()) {
            val msg = resp.errors?.joinToString { it.message }.orEmpty()
            throw RuntimeException(msg.ifBlank { "Error GraphQL (createUserLoan)" })
        }

        return resp.data?.createUserLoan
            ?: throw RuntimeException("Respuesta vacía (createUserLoan)")
    }
}