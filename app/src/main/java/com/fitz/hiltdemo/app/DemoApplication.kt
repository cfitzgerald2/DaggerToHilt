package com.fitz.hiltdemo.app

import android.app.Application
import com.fitz.hiltdemo.BuildConfig
import com.fitz.hiltdemo.di.component.AppComponent
import com.fitz.hiltdemo.di.component.DaggerAppComponent
import com.fitz.hiltdemo.di.module.AppModule
import com.fitz.movie.di.MovieComponentProvider
import timber.log.Timber

class DemoApplication: Application(), MovieComponentProvider {

    private lateinit var createdAppComponent: AppComponent

    override val movieComponent get() = createdAppComponent.provideMovieComponentFactory().create()

    override fun onCreate() {
        super.onCreate()

        createdAppComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .application(this)
            .build()

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}