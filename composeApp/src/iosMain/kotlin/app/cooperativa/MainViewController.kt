package app.cooperativa

import androidx.compose.ui.window.ComposeUIViewController
import app.cooperativa.di.KoinHelper

fun MainViewController() = ComposeUIViewController(
    configure = { KoinHelper.initialize()}
) { App() }