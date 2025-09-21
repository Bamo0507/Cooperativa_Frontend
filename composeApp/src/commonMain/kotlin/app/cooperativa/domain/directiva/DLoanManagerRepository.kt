package app.cooperativa.domain.directiva

import app.cooperativa.core.network.apollo.executeQuery
import app.cooperativa.data.model.dto.Member
import app.cooperativa.graphql.GettingAffiliatesQuery
import com.apollographql.apollo3.ApolloClient

interface DLoanManagerRepository {
    suspend fun getAllAffiliates(): List<Member>
    suspend fun submitLoan() // TBD cuando exista la mutation
}

class DirectiveLoanManagerRepository(
    private val apollo: ApolloClient
) : DLoanManagerRepository {

    override suspend fun getAllAffiliates(): List<Member> {
        return apollo.executeQuery(GettingAffiliatesQuery()) { data ->
            data.getAllMemembers.map { node ->
                Member(
                    userId = node.userId,
                    name = node.name
                )
            }
        }
    }

    override suspend fun submitLoan() {
        // TODO: mutation cuando esté lista
    }
}