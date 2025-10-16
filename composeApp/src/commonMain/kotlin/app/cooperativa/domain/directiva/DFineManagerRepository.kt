package app.cooperativa.domain.directiva

import app.cooperativa.core.network.apollo.executeQuery
import app.cooperativa.data.model.dto.Member
import app.cooperativa.graphql.CreateFineMutation
import app.cooperativa.graphql.GettingAffiliatesQuery
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse

interface DFineManagerRepository {
    suspend fun getAllAffiliates(): List<Member>
    suspend fun submitFine(affiliateKey: String, amount: Float, motive: String): String
}


class DirectiveFineManagerRepository(
    private val membersApollo: ApolloClient, // named("payment")
    private val fineApollo: ApolloClient // named("fine")
) : DFineManagerRepository {

    override suspend fun getAllAffiliates(): List<Member> {
        return membersApollo.executeQuery(GettingAffiliatesQuery()) { data ->
            data.getAllMembers.map { node ->
                Member(userId = node.userId, name = node.name)
            }
        }
    }

    override suspend fun submitFine(
        affiliateKey: String,
        amount: Float,
        motive: String
    ): String {
        val resp = fineApollo.mutation(
            CreateFineMutation(
                affiliateKey = affiliateKey,
                amount = amount.toDouble(),
                motive = motive
            )
        ).execute()

        if (resp.hasErrors()) {
            val msg = resp.errors?.joinToString { it.message }.orEmpty()
            throw RuntimeException(msg.ifBlank { "Error GraphQL (createFine)" })
        }

        return resp.data?.createFine ?: throw RuntimeException("Respuesta vacía (createFine)")
    }
}