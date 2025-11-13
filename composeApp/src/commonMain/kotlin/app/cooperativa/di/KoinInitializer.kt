package app.cooperativa.di

import app.cooperativa.di.auth.authModule
import app.cooperativa.di.dataStore.dataStoreModule
import app.cooperativa.di.dataStore.preferencesModule
import app.cooperativa.di.directiva.daccountmodule
import app.cooperativa.di.directiva.dfinemanagermodule
import app.cooperativa.di.directiva.dloanmanagermodule
import app.cooperativa.di.directiva.dpagosmodule
import app.cooperativa.di.directiva.dpaymentsdetailmodule
import app.cooperativa.di.directiva.dpendingpaymodule
import app.cooperativa.di.socios.saccountmodule
import app.cooperativa.di.socios.shistorialmodule
import app.cooperativa.di.socios.spagoEnviarModule
import app.cooperativa.di.socios.spagosStatusModule
import org.koin.core.context.startKoin
import org.koin.core.KoinApplication

// Aqui se deberan declarar todos los modulos de Koin
fun getKoinModules() = listOf(
    dpagosmodule,
    dpendingpaymodule,
    dsplashmodule,
    dpaymentsdetailmodule,
    dloanmanagermodule,

    shistorialmodule,
    spagosStatusModule,
    spagoEnviarModule,

    dataStoreModule,
    preferencesModule,

    generalmodule,

    authModule,

    saccountmodule,
    daccountmodule,

    dfinemanagermodule,

    coreNetworkModule,
)

object KoinHelper {
    fun initialize(
        config: (KoinApplication.() -> Unit)? = null
    ){
        startKoin {
            modules(getKoinModules())
            config?.invoke(this)
        }
    }
}