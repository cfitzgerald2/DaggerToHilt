package com.fitz.movie.di.module

import com.fitz.movie.usecase.logger.ActivityLogger
import com.fitz.movie.usecase.logger.Logger
import com.fitz.movie.usecase.logger.LoggerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class LoggerModule {

    @Binds
    @ActivityScoped
    @ActivityLogger
    abstract fun provideLogger(logger: LoggerImpl): Logger
}