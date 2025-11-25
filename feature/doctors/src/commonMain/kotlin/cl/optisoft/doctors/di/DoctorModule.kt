package cl.optisoft.doctors.di

import cl.optisoft.doctors.data.DataRepository
import cl.optisoft.doctors.data.remote.RemoteImpl
import cl.optisoft.doctors.data.repository.Remote
import cl.optisoft.doctors.data.source.Factory
import cl.optisoft.doctors.presentation.DoctorViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val doctorModules = module {

    singleOf(::RemoteImpl).bind<Remote>()
    singleOf(::Factory).bind<Factory>()
//    singleOf(::ArticleRepositoryImpl).bind<ArticleRepository>()
    factoryOf(::DataRepository)
    factoryOf(::Factory)
    viewModelOf(::DoctorViewModel)
//    viewModelOf(::ArticleDetailViewModel)
}