package app.cooperativa.di.socios

import app.cooperativa.domain.socios.MockSociosPagoErrorRepository
import app.cooperativa.domain.socios.SPagoErrorRepository
import app.cooperativa.presentation.mainflow.socios.pagos.pagoError.SPagoErrorViewModel
import org.koin.dsl.module

val spagoErrorModule = module {
    single<SPagoErrorRepository> { MockSociosPagoErrorRepository() }

    factory { (paymentId: String) -> SPagoErrorViewModel(get(), paymentId) }
}