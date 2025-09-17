package app.cooperativa.di.socios

import app.cooperativa.domain.socios.MockSociosPagosStatusRepository
import app.cooperativa.domain.socios.SPagosStatusRepository
import app.cooperativa.presentation.mainflow.socios.pagos.pagoStatus.SPagosStatusViewModel
import org.koin.dsl.module

val spagosStatusModule = module {
    // Declare current mock implementation to manage implementation
    single<SPagosStatusRepository> { MockSociosPagosStatusRepository() }

    factory {
        SPagosStatusViewModel(get())
    }
}