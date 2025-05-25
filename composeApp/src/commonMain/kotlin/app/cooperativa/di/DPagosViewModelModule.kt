package app.cooperativa.di

import app.cooperativa.domain.directiva.MockPaymentsRepository
import app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral.DPaymentsViewModel
import org.koin.dsl.module

val dpagosmodule = module {
    single { MockPaymentsRepository() }

    factory { DPaymentsViewModel( get() ) }
}