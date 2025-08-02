package app.cooperativa.graphql

import com.apollographql.apollo3.ApolloClient
import app.cooperativa.graphql.GetHistoryQuery
import com.apollographql.apollo3.api.ApolloResponse

class GraphQLClientProvider(
    private val endpoint: String
) {
    private val client: ApolloClient by lazy {
        ApolloClient.Builder()
            .serverUrl(endpoint)
            .build()
    }

    suspend fun getHistoryResponse(accessToken: String): ApolloResponse<GetHistoryQuery.Data> {
        return client.query(
            GetHistoryQuery(accessToken = accessToken)
        ).execute()
    }
}