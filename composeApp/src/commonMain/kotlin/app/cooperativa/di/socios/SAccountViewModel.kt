package app.cooperativa.di.socios

import app.cooperativa.presentation.mainflow.socios.account.mainAccount.SAccountViewModel
import org.koin.dsl.module

val saccountmodule = module {
    factory {
        SAccountViewModel(get())
    }
}