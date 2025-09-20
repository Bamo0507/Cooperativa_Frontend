package app.cooperativa.di.directiva

import app.cooperativa.domain.directiva.DFineManagerRepository
import app.cooperativa.domain.directiva.DirectiveFineManagerRepository
import app.cooperativa.presentation.mainflow.directiva.manager.fine.DFineManagerViewModel
import com.apollographql.apollo3.ApolloClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dfinemanagermodule = module {
    single<DFineManagerRepository> {
        DirectiveFineManagerRepository(get<ApolloClient>(named("payment")))
    }

    factory {
        DFineManagerViewModel(get())
    }
}