package com.fitz.movie.di.module

import androidx.lifecycle.ViewModelProvider
import com.fitz.movie.presentation.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}