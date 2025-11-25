package cl.optisoft.di

import cl.optisoft.di.qualifier.IODispatcher
import cl.optisoft.di.qualifier.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val dispatchersModule = module {
    single(IODispatcher) { Dispatchers.IO }
    single(MainDispatcher) { Dispatchers.Main }

    // Default dispatcher
    single { get<CoroutineDispatcher>(IODispatcher) }
}