package app.cooperativa.di.dataStore

import org.koin.dsl.module
import org.koin.core.module.Module

actual val dataStoreModule: Module
    get() = module { single { createDataStore() } }