package app.cooperativa.di

import app.cooperativa.domain.directiva.DPaymentsDetailRepository
import app.cooperativa.domain.directiva.DPaymentsRepository
import app.cooperativa.domain.directiva.MockPaymentsDetailRepository
import app.cooperativa.domain.directiva.MockPaymentsRepository
import app.cooperativa.presentation.mainflow.directiva.pagos.paymentDetail.DPaidPayViewModel
import org.koin.dsl.module

val dpaymentsdetailmodule = module {
    single<DPaymentsDetailRepository> { MockPaymentsDetailRepository() }

    factory { (paymentId: Int) -> DPaidPayViewModel(get(), paymentId) }
}