package app.cooperativa.di.socios

import app.cooperativa.domain.socios.SHistorialRepository
import app.cooperativa.domain.socios.SociosHistorialRepository
import app.cooperativa.presentation.mainflow.socios.historial.mainHistorial.SHistorialViewModel
import com.apollographql.apollo3.ApolloClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

val shistorialmodule = module {
    single<SHistorialRepository> {
        SociosHistorialRepository(
            apolloPayment = get<ApolloClient>(named("payment")),
            apolloLoan = get<ApolloClient>(named("loan"))
        )
    }

    factory {
        SHistorialViewModel(
            get(),
            get()
        )
    }
}