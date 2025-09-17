package app.cooperativa.core.network.apollo

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory

class PaymentApollo(endpoint: String) {
    private val cacheFactory = SqlNormalizedCacheFactory("payment_cache.db")

    val client: ApolloClient = ApolloClient.Builder()
        .serverUrl(endpoint)
        .normalizedCache(cacheFactory)
        .build()
}