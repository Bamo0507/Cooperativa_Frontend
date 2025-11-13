package app.cooperativa.di

import app.cooperativa.core.network.ktor.createPlatformHttpClient
import app.cooperativa.domain.general.TicketViewerRepository
import app.cooperativa.domain.general.TicketViewerRepositoryImpl
import org.koin.dsl.module
import org.koin.core.qualifier.named
import io.ktor.client.HttpClient

val generalmodule = module {
    single<HttpClient> { createPlatformHttpClient() }
    single<TicketViewerRepository> { TicketViewerRepositoryImpl(get<HttpClient>()) }
}