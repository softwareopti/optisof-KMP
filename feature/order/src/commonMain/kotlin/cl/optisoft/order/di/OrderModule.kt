package cl.optisoft.order.di

import cl.optisoft.order.data.DataRepository
import cl.optisoft.order.data.remote.RemoteImpl
import cl.optisoft.order.data.repository.Remote
import cl.optisoft.order.data.source.Factory
import cl.optisoft.order.presentation.OrderViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val orderModules = module {

    singleOf(::RemoteImpl).bind<Remote>()
    singleOf(::Factory).bind<Factory>()
    factoryOf(::DataRepository)
    factoryOf(::Factory)
    viewModelOf(::OrderViewModel)

}