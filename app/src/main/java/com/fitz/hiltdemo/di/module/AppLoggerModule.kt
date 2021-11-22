package com.fitz.hiltdemo.di.module

import com.fitz.movie.usecase.logger.AppLogger
import com.fitz.movie.usecase.logger.Logger
import com.fitz.movie.usecase.logger.LoggerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppLoggerModule {

    @Binds
    @Singleton
    @AppLogger
    abstract fun provideLogger(logger: LoggerImpl): Logger
}