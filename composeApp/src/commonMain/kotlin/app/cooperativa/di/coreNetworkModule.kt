package app.cooperativa.di

import app.cooperativa.core.network.apollo.PaymentApollo
import com.apollographql.apollo3.ApolloClient
import org.koin.dsl.module

val coreNetworkModule = module {
    single<ApolloClient> {
        PaymentApollo(
            endpoint = "https://dev.cooperativa-isp.cc/graphql/payment"
        ).client
    }
}