package com.fitz.hiltdemo.di.module

import com.fitz.hiltdemo.usecase.databridge.DataBridge
import com.fitz.hiltdemo.usecase.databridge.MovieDataBridge
import com.fitz.hiltdemo.usecase.model.MovieViewItem
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindModule(data: MovieDataBridge): DataBridge<MovieViewItem>
}