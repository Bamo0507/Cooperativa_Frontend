package app.cooperativa.di

import app.cooperativa.presentation.mainflow.directiva.splash.SplashViewModel
import org.koin.dsl.module

val dsplashmodule = module {
    factory { SplashViewModel() }
}