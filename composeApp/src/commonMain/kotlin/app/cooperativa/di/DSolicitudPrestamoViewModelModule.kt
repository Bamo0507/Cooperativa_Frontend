package app.cooperativa.di

import app.cooperativa.domain.directiva.MockSolicitudPrestamoRepository
import app.cooperativa.presentation.mainflow.directiva.prestamos.loanRequestDetail.SolicitudPrestamoViewModel
import org.koin.dsl.module

val dsolicitudprestamomodule = module {
    single { MockSolicitudPrestamoRepository() }

    factory { SolicitudPrestamoViewModel( get() ) }
}