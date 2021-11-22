package com.fitz.movie.usecase.logger

import javax.inject.Qualifier

interface Logger {
    fun log(message: String)
}

@Qualifier
@Retention(value = AnnotationRetention.BINARY)
annotation class AppLogger

@Qualifier
@Retention(value = AnnotationRetention.BINARY)
annotation class ActivityLogger