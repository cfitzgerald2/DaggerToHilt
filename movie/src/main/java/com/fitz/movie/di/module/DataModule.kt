package com.fitz.movie.di.module

import com.fitz.movie.usecase.databridge.DataBridge
import com.fitz.movie.usecase.databridge.MovieDataBridge
import com.fitz.movie.usecase.model.MovieViewItem
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindDataBridge(data: MovieDataBridge): DataBridge<MovieViewItem>
}