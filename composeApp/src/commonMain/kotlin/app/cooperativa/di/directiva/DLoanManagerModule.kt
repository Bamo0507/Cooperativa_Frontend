package app.cooperativa.di.directiva

import app.cooperativa.domain.directiva.DLoanManagerRepository
import app.cooperativa.domain.directiva.DirectiveLoanManagerRepository
import app.cooperativa.presentation.mainflow.directiva.manager.loan.DLoanManagerViewModel
import com.apollographql.apollo3.ApolloClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dloanmanagermodule = module {
    single<DLoanManagerRepository> {
        DirectiveLoanManagerRepository(
            apolloPayment = get<ApolloClient>(named("payment")),
            apolloLoan = get<ApolloClient>(named("loan"))
        )
    }
    factory { DLoanManagerViewModel(get()) }
}