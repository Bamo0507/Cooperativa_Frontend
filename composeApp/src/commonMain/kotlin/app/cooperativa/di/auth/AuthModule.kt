package app.cooperativa.di.auth

import app.cooperativa.domain.login.LoginRepository
import app.cooperativa.presentation.login.LoginViewModel
import app.cooperativa.domain.localstorage.PreferencesLocalStorage
import app.cooperativa.domain.login.CoopLoginRepository
import app.cooperativa.graphql.GraphQLClientProvider
import org.koin.dsl.module

val authModule = module {
    single {
        GraphQLClientProvider(
            endpoint = "https://dev.cooperativa-isp.cc/general/login"
        )
    }

    single<LoginRepository> {
        CoopLoginRepository(get())
    }

    factory {
        LoginViewModel(get(), get())
    }
}