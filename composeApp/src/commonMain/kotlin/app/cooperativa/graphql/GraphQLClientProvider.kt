package app.cooperativa.graphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import app.cooperativa.graphql.GetHistoryQuery
import app.cooperativa.graphql.LoginMutation
import app.cooperativa.graphql.type.LoginInput

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

    suspend fun login(userName: String, passCode: String): ApolloResponse<LoginMutation.Data> {
        val input = LoginInput(
            user_name = userName,
            pass_code = passCode
        )
        return client.mutation(LoginMutation(input = input)).execute()
    }
}