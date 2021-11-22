package com.fitz.hiltdemo.app

import android.app.Application
import android.os.Build
import com.fitz.hiltdemo.BuildConfig
import com.fitz.hiltdemo.di.component.AppComponent
import com.fitz.hiltdemo.di.component.DaggerAppComponent
import com.fitz.hiltdemo.di.module.AppModule
import timber.log.Timber

class DemoApplication: Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}