package app.cooperativa.di.dataStore

import app.cooperativa.data.preferences.PreferencesDataStore
import app.cooperativa.domain.localstorage.PreferencesLocalStorage
import org.koin.dsl.module

val preferencesModule = module {
    single<PreferencesLocalStorage> { PreferencesDataStore(get()) }
}