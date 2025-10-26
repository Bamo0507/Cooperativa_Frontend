package app.cooperativa.di.directiva

import app.cooperativa.domain.directiva.DPaymentsDetailRepository
import app.cooperativa.domain.directiva.DirectivePaymentsDetailRepository
import app.cooperativa.presentation.mainflow.directiva.pagos.paymentDetail.DPaidPayViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dpaymentsdetailmodule = module {
    single<DPaymentsDetailRepository> {
        DirectivePaymentsDetailRepository(get(named("payment")))
    }
    factory { (paymentId: String) -> DPaidPayViewModel(get(), paymentId) }
}