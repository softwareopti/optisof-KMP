package cl.optisoft.optisoft_kmp

import android.app.Application
import cl.optisoft.di.startAndroidKoin

class OptiSoftApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()

    }

    private fun startKoin() {
        startAndroidKoin(this)
    }
}