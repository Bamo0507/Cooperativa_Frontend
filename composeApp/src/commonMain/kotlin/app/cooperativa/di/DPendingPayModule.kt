package app.cooperativa.di

import app.cooperativa.domain.directiva.DPendingPayRepository
import app.cooperativa.domain.directiva.MockPendingPayRepository
import app.cooperativa.presentation.mainflow.directiva.pagos.pendingPaymentDetail.DPendingPayViewModel
import org.koin.dsl.module

val dpendingpaymodule = module {
    single<DPendingPayRepository> { MockPendingPayRepository() }

    factory { (paymentId: Int) -> DPendingPayViewModel(get(), paymentId) }
}