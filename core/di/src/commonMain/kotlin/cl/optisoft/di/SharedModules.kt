package cl.optisoft.di

import cl.optisoft.doctors.di.doctorModules
import cl.optisoft.network.di.networkModule
import cl.optisoft.order.di.orderModules

internal val sharedModules = listOf(
    networkModule,
    dispatchersModule,
    doctorModules,
    orderModules
)