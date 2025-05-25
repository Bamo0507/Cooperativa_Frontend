package app.cooperativa.di

import app.cooperativa.presentation.mainflow.directiva.prestamos.prestamosGeneral.DPrestamoViewModel
import org.koin.dsl.module

val DPrestamosViewModelModule = module {
    factory { DPrestamoViewModel() }
}