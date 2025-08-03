package app.cooperativa.di

import app.cooperativa.di.auth.authModule
import app.cooperativa.di.dataStore.dataStoreModule
import app.cooperativa.di.dataStore.preferencesModule
import app.cooperativa.di.directiva.dfinesmodule
import app.cooperativa.di.directiva.dpagaresmodule
import app.cooperativa.di.directiva.dpagosmodule
import app.cooperativa.di.directiva.dpaymentsdetailmodule
import app.cooperativa.di.directiva.dpendingpaymodule
import app.cooperativa.di.directiva.dprestamosmodule
import app.cooperativa.di.directiva.dsolicitudprestamomodule
import app.cooperativa.di.socios.shistorialmodule
import app.cooperativa.di.socios.spagoEnviarModule
import app.cooperativa.di.socios.spagosStatusModule
import app.cooperativa.di.socios.sprestamomodule
import org.koin.core.context.startKoin
import org.koin.core.KoinApplication

// Aqui se deberan declarar todos los modulos de Koin
fun getKoinModules() = listOf(
    dprestamosmodule,
    dsolicitudprestamomodule,
    dpagosmodule,
    dpendingpaymodule,
    dsplashmodule,
    dpaymentsdetailmodule,
    dfinesmodule,
    dpagaresmodule,

    shistorialmodule,
    sprestamomodule,
    spagosStatusModule,
    spagoEnviarModule,

    dataStoreModule,
    preferencesModule,

    authModule
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