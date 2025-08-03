package app.cooperativa.di

import app.cooperativa.presentation.mainflow.splash.SplashViewModel
import org.koin.dsl.module

val dsplashmodule = module {
    factory { SplashViewModel(get()) }
}
