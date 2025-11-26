package cl.optisoft.optisoft_kmp

import androidx.compose.ui.window.ComposeUIViewController
import cl.optisoft.di.startIosKoin

fun MainViewController() = ComposeUIViewController(configure = {
    startIosKoin()
}) { App() }