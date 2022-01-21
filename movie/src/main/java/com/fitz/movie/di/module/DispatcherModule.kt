package com.fitz.movie.di.module

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class DispatcherModule {

    @Provides
    fun bindCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}