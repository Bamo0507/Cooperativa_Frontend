package app.cooperativa.di.socios

import app.cooperativa.domain.socios.MockSociosPagoEnviarRepository
import app.cooperativa.domain.socios.SPagoEnviarRepository
import app.cooperativa.presentation.mainflow.socios.pagos.agregarPago.SPagoEnviarViewModel
import org.koin.dsl.module

val spagoEnviarModule = module {
    // Declare current mock implementation
    single<SPagoEnviarRepository> { MockSociosPagoEnviarRepository() }

    factory {
        SPagoEnviarViewModel(get(), get())
    }
}