package app.cooperativa.di.socios

import app.cooperativa.domain.socios.SHistorialRepository
import app.cooperativa.domain.socios.SociosHistorialRepository
import app.cooperativa.graphql.GraphQLClientProvider
import app.cooperativa.presentation.mainflow.socios.historial.mainHistorial.SHistorialViewModel
import org.koin.dsl.module

val shistorialmodule = module {
    single {
        GraphQLClientProvider(
            endpoint = "https://dev.cooperativa-isp.cc/graphql/payment"
        )
    }

    single<SHistorialRepository> {
        SociosHistorialRepository(get())
    }

    factory {
        SHistorialViewModel(get())
    }
}