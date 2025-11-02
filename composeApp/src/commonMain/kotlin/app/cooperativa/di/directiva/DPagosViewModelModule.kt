package app.cooperativa.di.directiva

import app.cooperativa.domain.directiva.DPaymentsRepository
import app.cooperativa.domain.directiva.DirectivePaymentsRepository
import app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral.DPaymentsViewModel
import com.apollographql.apollo3.ApolloClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dpagosmodule = module {
    single<DPaymentsRepository> {
        DirectivePaymentsRepository(
            get<ApolloClient>(named("payment")),
            get<ApolloClient>(named("fine"))
        )
    }

    factory { DPaymentsViewModel(get()) }
}
