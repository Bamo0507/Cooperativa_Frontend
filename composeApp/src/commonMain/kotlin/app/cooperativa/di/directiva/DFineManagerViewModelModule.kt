package app.cooperativa.di.directiva

import app.cooperativa.domain.directiva.DFineManagerRepository
import app.cooperativa.domain.directiva.DirectiveFineManagerRepository
import app.cooperativa.graphql.GraphQLClientProvider
import app.cooperativa.presentation.mainflow.directiva.manager.fine.DFineManagerViewModel
import org.koin.dsl.module

val dfinemanagermodule = module {
    single {
        GraphQLClientProvider(
            endpoint = "https://dev.cooperativa-isp.cc/graphql/payment"
        )
    }

    single<DFineManagerRepository> {
        DirectiveFineManagerRepository(get())
    }

    factory {
        DFineManagerViewModel(get())
    }
}