package app.cooperativa.di.auth

import app.cooperativa.core.network.ktor.createPlatformHttpClient
import app.cooperativa.domain.login.CoopLoginRepository
import app.cooperativa.domain.login.LoginRepository
import app.cooperativa.presentation.login.LoginViewModel
import org.koin.dsl.module

val authModule = module {
    single { createPlatformHttpClient() }

    single<LoginRepository> {
        CoopLoginRepository(get())
    }

    factory {
        LoginViewModel(get(), get())
    }
}