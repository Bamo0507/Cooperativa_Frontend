package app.cooperativa.di.directiva

import app.cooperativa.domain.directiva.DPendingPayRepository
import app.cooperativa.domain.directiva.DirectivePendingPayRepository
import app.cooperativa.presentation.mainflow.directiva.pagos.pendingPaymentDetail.DPendingPayViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import com.apollographql.apollo3.ApolloClient

val dpendingpaymodule = module {
    single<DPendingPayRepository> {
        DirectivePendingPayRepository(
            apollo = get<ApolloClient>(named("payment"))
        )
    }

    // Factory con parámetro paymentId
    factory { (paymentId: String) ->
        DPendingPayViewModel(
            repository = get(),
            paymentId = paymentId
        )
    }
}