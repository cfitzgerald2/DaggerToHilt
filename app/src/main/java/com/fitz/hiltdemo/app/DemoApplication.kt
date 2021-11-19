package com.fitz.hiltdemo.app

import android.app.Application
import com.fitz.hiltdemo.di.component.AppComponent
import com.fitz.hiltdemo.di.component.DaggerAppComponent
import com.fitz.hiltdemo.di.module.AppModule

class DemoApplication: Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}