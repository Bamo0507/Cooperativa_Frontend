package app.cooperativa.core.network.apollo

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Query
import com.apollographql.apollo3.api.Mutation

class GraphQlException(message: String): RuntimeException(message)

/** Helper para Queries */
suspend fun <D : Query.Data, T> ApolloClient.executeQuery(
    query: Query<D>,
    map: (D) -> T
): T {
    val response = this.query(query).execute()
    if (response.hasErrors()) {
        val msg = response.errors?.joinToString { it.message }.orEmpty()
        throw GraphQlException(msg.ifBlank { "Error GraphQL" })
    }
    val data = response.data ?: throw GraphQlException("Respuesta vacía de GraphQL")
    return map(data)
}

/** Helper para Mutations */
suspend fun <D : Mutation.Data, T> ApolloClient.executeMutation(
    mutation: Mutation<D>,
    map: (D) -> T
): T {
    val response = this.mutation(mutation).execute()
    if (response.hasErrors()) {
        val msg = response.errors?.joinToString { it.message }.orEmpty()
        throw GraphQlException(msg.ifBlank { "Error GraphQL" })
    }
    val data = response.data ?: throw GraphQlException("Respuesta vacía de GraphQL")
    return map(data)
}