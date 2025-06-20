package app.cooperativa.di.directiva

import app.cooperativa.domain.directiva.DPagaresRepository
import app.cooperativa.domain.directiva.MockPagaresRepository
import app.cooperativa.presentation.mainflow.directiva.prestamos.pagaresDetail.DPagaresViewModel
import org.koin.dsl.module

val dpagaresmodule = module {
    single<DPagaresRepository> { MockPagaresRepository() }

    factory { (pagareId: Int) ->
        DPagaresViewModel(get(), pagareId)
    }
}