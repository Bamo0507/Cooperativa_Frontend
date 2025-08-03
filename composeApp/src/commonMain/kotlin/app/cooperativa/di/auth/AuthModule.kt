package app.cooperativa.di.auth

import app.cooperativa.domain.login.CoopLoginRepository
import app.cooperativa.domain.login.LoginRepository
import app.cooperativa.presentation.login.LoginViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val authModule = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            expectSuccess = false
        }
    }

    single<LoginRepository> {
        CoopLoginRepository(get())
    }

    factory {
        LoginViewModel(get(), get())
    }
}