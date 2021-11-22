package com.fitz.hiltdemo.di.component

import android.app.Application
import com.fitz.hiltdemo.di.module.AppModule
import com.fitz.movie.di.component.MovieComponent
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [AppModule::class]
)
interface AppComponent {

    fun provideMovieComponentFactory(): MovieComponent.Factory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }
}