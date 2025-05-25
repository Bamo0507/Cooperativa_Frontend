package app.cooperativa.di

import org.koin.compose.getKoin
import org.koin.core.context.startKoin
import org.koin.core.KoinApplication

// Aqui se deberan declarar todos los modulos de Koin
fun getKoinModules() = listOf(
    dprestamosmodule,
    dsolicitudprestamomodule,
    dpagosmodule,
    dpendingpaymodule,
    dsplashmodule,
    dpaymentsdetailmodule
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