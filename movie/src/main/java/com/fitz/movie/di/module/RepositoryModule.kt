package com.fitz.movie.di.module

import com.fitz.movie.persistence.dao.MovieDao
import com.fitz.movie.persistence.db.MovieDatabase
import com.fitz.movie.service.MovieService
import com.fitz.movie.service.ServiceImplementations
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