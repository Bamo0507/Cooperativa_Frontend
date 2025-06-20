package app.cooperativa.di.socios

import app.cooperativa.domain.socios.MockSociosHistorialRepository
import app.cooperativa.domain.socios.SHistorialRepository
import app.cooperativa.presentation.mainflow.socios.historial.mainHistorial.SHistorialViewModel
import org.koin.dsl.module

val shistorialmodule = module {
    single<SHistorialRepository> { MockSociosHistorialRepository() }

    factory {
        SHistorialViewModel(get())
    }
}