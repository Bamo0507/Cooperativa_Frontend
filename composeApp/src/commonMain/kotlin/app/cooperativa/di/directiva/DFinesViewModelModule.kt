package app.cooperativa.di.directiva

import app.cooperativa.domain.directiva.DFinesRepository
import app.cooperativa.domain.directiva.FinesRepository
import app.cooperativa.presentation.mainflow.directiva.pagos.fineSelection.FineViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dfinesmodule = module {
    single<DFinesRepository> {
        FinesRepository(fineApollo = get(named("fine")))
    }

    factory { (accessKey: String) ->
        FineViewModel(
            repository = get<DFinesRepository>(),
            accessKey = accessKey
        )
    }
}