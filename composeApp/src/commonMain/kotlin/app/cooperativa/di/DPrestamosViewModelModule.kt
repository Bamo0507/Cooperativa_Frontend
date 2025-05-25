package app.cooperativa.di

import app.cooperativa.domain.MockPrestamosRepository
import app.cooperativa.presentation.mainflow.directiva.prestamos.prestamosGeneral.DPrestamoViewModel
import org.koin.dsl.module

val dprestamosmodule = module {
    single { MockPrestamosRepository() }

    factory { DPrestamoViewModel( get() ) }
}