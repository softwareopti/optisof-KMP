package cl.optisoft.di

import org.koin.core.context.startKoin

fun startIosKoin() {
    startKoin {
        modules(sharedModules)
    }
}