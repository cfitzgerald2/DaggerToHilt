package com.fitz.hiltdemo.di.module

import androidx.lifecycle.ViewModel
import com.fitz.hiltdemo.presentation.viewmodel.FirstFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FirstFragmentViewModel::class)
    abstract fun bindIncomeRecommendationViewModel(viewModel: FirstFragmentViewModel): ViewModel
}