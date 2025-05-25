package app.cooperativa.di

import app.cooperativa.domain.directiva.DSolicitudPrestamoRepository
import app.cooperativa.domain.directiva.MockSolicitudPrestamoRepository
import app.cooperativa.presentation.mainflow.directiva.prestamos.loanRequestDetail.SolicitudPrestamoViewModel
import org.koin.dsl.module

val dsolicitudprestamomodule = module {
    single<DSolicitudPrestamoRepository> { MockSolicitudPrestamoRepository() }

    factory { (solicitudId: Int) ->
        SolicitudPrestamoViewModel(
            repository = get(),
            solicitudId = solicitudId
        )
    }
}