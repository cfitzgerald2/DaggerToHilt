package com.fitz.hiltdemo.di.component

import com.fitz.hiltdemo.di.module.*
import com.fitz.hiltdemo.presentation.view.fragment.FirstFragment
import com.fitz.hiltdemo.presentation.view.activity.ListActivity
import dagger.Component

@Component(modules = [
    AppModule::class,
    ViewModelModule::class,
    ViewModelFactoryModule::class,
    DispatcherModule::class,
    DataModule::class
])
interface AppComponent {

    fun inject(activity: ListActivity)

    fun inject(firstFragment: FirstFragment)
}