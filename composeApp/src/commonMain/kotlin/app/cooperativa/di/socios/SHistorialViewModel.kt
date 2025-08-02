package app.cooperativa.di.socios

import app.cooperativa.domain.socios.SHistorialRepository
import app.cooperativa.domain.socios.SociosHistorialRepository
import app.cooperativa.graphql.GraphQLClientProvider
import app.cooperativa.presentation.mainflow.socios.historial.mainHistorial.SHistorialViewModel
import org.koin.dsl.module

val shistorialmodule = module {
    single {
        GraphQLClientProvider(
            endpoint = "https://dev.cooperativa-isp.cc/graphql/payment",
            accessTokenProvider = { "77656D82A042ABA5AE02293A880479D3DACA6609331486E01F351285990F6235" }
            //TODO: Inyectar de datastore el accesstoken
        )
    }

    single<SHistorialRepository> {
        SociosHistorialRepository(get())
    }

    factory {
        SHistorialViewModel(get())
    }
}