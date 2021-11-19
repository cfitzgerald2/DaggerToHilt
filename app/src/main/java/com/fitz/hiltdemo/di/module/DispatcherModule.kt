package com.fitz.hiltdemo.di.module

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
class DispatcherModule {

    @Provides
    fun bindCoroutineDispatcher(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }
}