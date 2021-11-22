package com.fitz.movie.di.component

import com.fitz.movie.di.module.*
import com.fitz.movie.presentation.view.activity.ListActivity
import com.fitz.movie.presentation.view.fragment.FirstFragment
import com.fitz.movie.presentation.view.fragment.SecondFragment
import dagger.Subcomponent

@Subcomponent(modules = [
    ViewModelModule::class,
    ViewModelFactoryModule::class,
    DispatcherModule::class,
    DataModule::class,
    RepositoryModule::class
])
interface MovieComponent {

    fun inject(activity: ListActivity)

    fun inject(firstFragment: FirstFragment)

    fun inject(firstFragment: SecondFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): MovieComponent
    }
}