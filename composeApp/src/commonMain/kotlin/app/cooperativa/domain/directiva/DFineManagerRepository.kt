package app.cooperativa.domain.directiva

import app.cooperativa.core.network.apollo.executeQuery
import app.cooperativa.data.model.dto.Member
import app.cooperativa.graphql.GettingAffiliatesQuery
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse

interface DFineManagerRepository {
    suspend fun getAllAffiliates(): List<Member>
    suspend fun submitFine() // TBD
}

class DirectiveFineManagerRepository(
    private val apollo: ApolloClient
) : DFineManagerRepository {

    override suspend fun getAllAffiliates(): List<Member> {
        return apollo.executeQuery(GettingAffiliatesQuery()) { data ->
            data.getAllMembers.map { node ->
                Member(
                    userId = node.userId,
                    name = node.name
                )
            }
        }
    }

    override suspend fun submitFine() {
        // TODO cuando exista la mutation
    }
}