package app.cooperativa.di.directiva

import app.cooperativa.domain.directiva.DFineManagerRepository
import app.cooperativa.domain.directiva.DirectiveFineManagerRepository
import app.cooperativa.presentation.mainflow.directiva.manager.fine.DFineManagerViewModel
import com.apollographql.apollo3.ApolloClient
import org.koin.dsl.module

val dfinemanagermodule = module {
    single<DFineManagerRepository> {
        // Koin te da el ApolloClient del coreNetworkModule
        DirectiveFineManagerRepository(get<ApolloClient>())
    }

    factory {
        DFineManagerViewModel(get())
    }
}