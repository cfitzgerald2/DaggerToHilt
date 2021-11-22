package com.fitz.hiltdemo.di.module

import com.fitz.hiltdemo.persistence.dao.MovieDao
import com.fitz.hiltdemo.persistence.db.MovieDatabase
import com.fitz.hiltdemo.service.MovieService
import com.fitz.hiltdemo.service.ServiceImplementations
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideMovieService(): MovieService {
        return ServiceImplementations.movieService
    }

    @Provides
    fun providesMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }
}