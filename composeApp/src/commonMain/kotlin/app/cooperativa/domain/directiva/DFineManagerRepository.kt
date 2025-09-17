package app.cooperativa.domain.directiva

import app.cooperativa.core.network.apollo.executeQuery
import app.cooperativa.graphql.GettingAffiliatesQuery
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse

data class Member(
    val usuarioId: Int,
    val name: String
)

interface DFineManagerRepository {
    suspend fun getAllAffiliates(): List<Member>
    suspend fun submitFine() // TBD
}

class DirectiveFineManagerRepository(
    private val apollo: ApolloClient
) : DFineManagerRepository {

    override suspend fun getAllAffiliates(): List<Member> {
        return apollo.executeQuery(GettingAffiliatesQuery()) { data ->
            data.getAllMemembers.map { node ->
                Member(
                    usuarioId = node.usuarioId, // no null
                    name = node.name            // no null
                )
            }
        }
    }

    override suspend fun submitFine() {
        // TODO cuando exista la mutation
    }
}