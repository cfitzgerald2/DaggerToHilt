package com.fitz.movie.di.module

import com.fitz.movie.usecase.databridge.DataBridge
import com.fitz.movie.usecase.databridge.MovieDataBridge
import com.fitz.movie.usecase.model.MovieViewItem
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindModule(data: MovieDataBridge): DataBridge<MovieViewItem>
}