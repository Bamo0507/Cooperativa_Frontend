package app.cooperativa.graphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.network.http.HttpNetworkTransport
import app.cooperativa.graphql.GetHistoryQuery
import app.cooperativa.graphql.LoginQuery

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

    suspend fun login(userName: String, passCode: String): ApolloResponse<LoginQuery.Data> {
        return client.query(
            LoginQuery(user_name = userName, pass_code = passCode)
        )
            .httpMethod(com.apollographql.apollo3.api.http.HttpMethod.Get)
            .execute()
    }
}