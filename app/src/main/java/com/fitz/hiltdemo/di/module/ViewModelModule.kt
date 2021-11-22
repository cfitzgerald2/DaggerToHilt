package com.fitz.hiltdemo.di.module

import androidx.lifecycle.ViewModel
import com.fitz.hiltdemo.presentation.viewmodel.FirstFragmentViewModel
import com.fitz.hiltdemo.presentation.viewmodel.SecondFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FirstFragmentViewModel::class)
    abstract fun bindFirstFragmentViewModel(viewModel: FirstFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SecondFragmentViewModel::class)
    abstract fun bindSecondFragmentViewModel(viewModel: SecondFragmentViewModel): ViewModel
}