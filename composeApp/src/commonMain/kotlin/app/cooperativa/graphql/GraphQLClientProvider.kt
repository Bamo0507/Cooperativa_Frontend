package app.cooperativa.graphql

import com.apollographql.apollo3.ApolloClient

class GraphQLClientProvider(
    private val endpoint: String,
    private val accessTokenProvider: suspend () -> String
) {
    // No cacheamos el token aquí porque podría cambiar; reconstruimos por llamada ligera
    suspend fun getClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(endpoint)
            .build()
    }

    suspend fun getHistoryResponse(): com.apollographql.apollo3.api.ApolloResponse<GetHistoryQuery.Data> {
        val client = getClient()
        val token = accessTokenProvider()
        return client.query(
            GetHistoryQuery(accessToken = token)
        ).execute()
    }
}