package app.cooperativa.di.socios

import app.cooperativa.domain.socios.MockSociosPrestamoRepository
import app.cooperativa.domain.socios.SPrestamoRepository
import app.cooperativa.presentation.mainflow.socios.prestamos.mainPrestamos.SPrestamoViewModel
import org.koin.dsl.module

val sprestamomodule = module {
    single<SPrestamoRepository> { MockSociosPrestamoRepository()}

    factory {
        SPrestamoViewModel(get())
    }


}