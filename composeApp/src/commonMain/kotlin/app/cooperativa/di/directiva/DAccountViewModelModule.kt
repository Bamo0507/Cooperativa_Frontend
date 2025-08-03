package app.cooperativa.di.directiva

import app.cooperativa.presentation.mainflow.directiva.account.mainAccount.DirectivaAccountViewModel
import app.cooperativa.presentation.mainflow.socios.account.mainAccount.SAccountViewModel
import org.koin.dsl.module

val daccountmodule = module {
    factory {
        DirectivaAccountViewModel(get())
    }
}