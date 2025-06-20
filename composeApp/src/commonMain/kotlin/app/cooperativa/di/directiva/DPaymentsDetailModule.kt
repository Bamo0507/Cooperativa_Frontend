package app.cooperativa.di.directiva

import app.cooperativa.domain.directiva.DPaymentsDetailRepository
import app.cooperativa.domain.directiva.MockPaymentsDetailRepository
import app.cooperativa.presentation.mainflow.directiva.pagos.paymentDetail.DPaidPayViewModel
import org.koin.dsl.module

val dpaymentsdetailmodule = module {
    single<DPaymentsDetailRepository> { MockPaymentsDetailRepository() }

    factory { (paymentId: Int) -> DPaidPayViewModel(get(), paymentId) }
}