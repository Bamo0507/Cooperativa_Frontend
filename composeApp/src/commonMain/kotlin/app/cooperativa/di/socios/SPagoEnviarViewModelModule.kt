package app.cooperativa.di.socios

import app.cooperativa.core.network.ktor.createPlatformHttpClient
import app.cooperativa.domain.localstorage.PreferencesLocalStorage
import app.cooperativa.domain.socios.SPagoEnviarRepository
import app.cooperativa.domain.socios.SociosPagoEnviarRepository
import app.cooperativa.presentation.mainflow.socios.pagos.agregarPago.SPagoEnviarViewModel
import com.apollographql.apollo3.ApolloClient
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

val spagoEnviarModule = module {
    single<HttpClient> { createPlatformHttpClient() }

    single<SPagoEnviarRepository> {
        SociosPagoEnviarRepository(
            fineApollo = get<ApolloClient>(named("fine")),
            quotaApollo = get<ApolloClient>(named("quota")),
            paymentApollo = get<ApolloClient>(named("payment")),
            http = get<HttpClient>()
        )
    }

    factory {
        SPagoEnviarViewModel(
            repository = get(),
            prefs = get()
        )
    }
}
