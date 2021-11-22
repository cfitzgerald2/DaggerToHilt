package com.fitz.hiltdemo.di.module

import android.content.Context
import com.fitz.movie.persistence.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule{

    @Provides
    fun provideApplication(@ApplicationContext context: Context): Context = context

    @Provides
    fun provideDatabase(context: Context) : MovieDatabase {
        return MovieDatabase.getDatabase(context)
    }
}