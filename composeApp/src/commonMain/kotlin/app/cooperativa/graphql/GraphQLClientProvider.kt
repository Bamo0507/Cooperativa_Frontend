package app.cooperativa.graphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse

class GraphQLClientProvider(
    private val endpoint: String
) {
    private val client: ApolloClient by lazy {
        ApolloClient.Builder()
            .serverUrl(endpoint)
            .build()
    }

    // Query for Affiliates
    //================================================================
    suspend fun getHistoryResponse(accessToken: String): ApolloResponse<GetHistoryQuery.Data> {
        return client.query(
            GetHistoryQuery(accessToken = accessToken)
        ).execute()
    }

    // Shared Queries
    //================================================================
    suspend fun getAllAffiliates(): ApolloResponse<GettingAffiliatesQuery.Data> {
        return client.query(
            GettingAffiliatesQuery()
        ).execute()
    }
}