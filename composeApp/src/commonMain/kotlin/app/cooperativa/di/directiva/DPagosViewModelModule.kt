package app.cooperativa.di.directiva

import app.cooperativa.domain.directiva.DPaymentsRepository
import app.cooperativa.domain.directiva.MockPaymentsRepository
import app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral.DPaymentsViewModel
import org.koin.dsl.module

val dpagosmodule = module {
    single<DPaymentsRepository> { MockPaymentsRepository() }

    factory { DPaymentsViewModel( get() ) }
}