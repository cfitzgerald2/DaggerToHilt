package com.fitz.hiltdemo.di.module

import android.content.Context
import com.fitz.hiltdemo.app.DemoApplication
import com.fitz.movie.di.component.MovieComponent
import com.fitz.movie.di.module.*
import com.fitz.movie.persistence.db.MovieDatabase
import dagger.Module
import dagger.Provides

@Module(
    subcomponents = [
        MovieComponent::class
    ]
)
class AppModule(private val application: DemoApplication) {

    @Provides
    fun provideApplication(): Context = application

    @Provides
    fun provideDatabase() : MovieDatabase {
        return MovieDatabase.getDatabase(application)
    }
}