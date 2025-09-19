package app.cooperativa.di

import app.cooperativa.core.network.apollo.PaymentApollo
import com.apollographql.apollo3.ApolloClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

private fun apollo(endpoint: String): ApolloClient =
    ApolloClient.Builder()
        .serverUrl(endpoint)
        .build()

val coreNetworkModule = module {
    // Cliente para /graphql/payment
    single(named("payment")) {
        apollo("https://dev.cooperativa-isp.cc/graphql/payment")
    }

    // Cliente para /graphql/fine
    single(named("fine")) {
        apollo("https://dev.cooperativa-isp.cc/graphql/fine")
    }

}