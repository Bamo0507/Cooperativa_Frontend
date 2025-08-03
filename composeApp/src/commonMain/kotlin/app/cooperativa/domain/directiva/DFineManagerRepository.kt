package app.cooperativa.domain.directiva

import app.cooperativa.graphql.GettingAffiliatesQuery
import app.cooperativa.graphql.GraphQLClientProvider
import com.apollographql.apollo3.api.ApolloResponse

data class Member(
    val usuarioId: Int,
    val name: String
)

interface DFineManagerRepository {
    suspend fun getAllAffiliates(): List<Member>
    suspend fun submitFine() //TBD
}

class DirectiveFineManagerRepository(
    private val clientProvider: GraphQLClientProvider
) : DFineManagerRepository {
    // TODO: Implementar en el client cuando se tenga
    override suspend fun submitFine() {
        TODO("Not yet implemented")
    }

    override suspend fun getAllAffiliates(): List<Member> {
        val response = clientProvider.getAllAffiliates()

        if (response.hasErrors()) {
            val msg = response.errors?.joinToString { it.message } ?: "Error de GraphQL"
            throw Exception(msg)
        }

        val members = response.data?.getAllMemembers
            ?: throw Exception("Ooops, no se pudo obtener los socios!")

        return members.map {
            Member(
                usuarioId = it.usuarioId,
                name = it.name
            )
        }
    }
}
