package com.fitz.hiltdemo.app

import android.app.Application
import android.os.CountDownTimer
import com.fitz.hiltdemo.BuildConfig
import com.fitz.movie.usecase.logger.AppLogger
import com.fitz.movie.usecase.logger.Logger
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class DemoApplication: Application() {

    @Inject
    @AppLogger
    lateinit var logger: Logger

    @Inject
    lateinit var appTimer: CountDownTimer

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        logger.log("app created")
        appTimer.start()
    }
}