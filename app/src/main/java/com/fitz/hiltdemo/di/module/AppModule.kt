package com.fitz.hiltdemo.di.module

import android.content.Context
import com.fitz.hiltdemo.app.DemoApplication
import com.fitz.hiltdemo.persistence.db.MovieDatabase
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: DemoApplication) {

    @Provides
    fun provideApplication(): Context = application

    @Provides
    fun provideDatabase() : MovieDatabase {
        return MovieDatabase.getDatabase(application)
    }
}