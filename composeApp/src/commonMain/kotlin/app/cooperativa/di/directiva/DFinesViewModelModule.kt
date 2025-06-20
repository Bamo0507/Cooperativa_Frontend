package app.cooperativa.di.directiva

import app.cooperativa.domain.directiva.DFinesRepository
import app.cooperativa.domain.directiva.MockFinesRepository
import app.cooperativa.presentation.mainflow.directiva.pagos.fineSelection.FineViewModel
import org.koin.dsl.module

val dfinesmodule = module {
    single<DFinesRepository> { MockFinesRepository() }

    factory { (userId: Int) ->
        FineViewModel(get(), userId)
    }
}