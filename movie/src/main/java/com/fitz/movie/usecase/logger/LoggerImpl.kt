package com.fitz.movie.usecase.logger

import timber.log.Timber
import javax.inject.Inject

class LoggerImpl @Inject constructor(): Logger {
    private var logCount: Int = 0
    override fun log(message: String) {
        Timber.d("Log $logCount from ${hashCode()} -> $message")
        logCount++
    }
}