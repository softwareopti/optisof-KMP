package cl.optisoft.di

import cl.optisoft.doctors.di.doctorModules
import cl.optisoft.network.di.networkModule

internal val sharedModules = listOf(
    networkModule,
    dispatchersModule,
    doctorModules,
)