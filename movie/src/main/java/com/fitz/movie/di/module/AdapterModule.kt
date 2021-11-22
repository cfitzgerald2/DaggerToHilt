package com.fitz.movie.di.module

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fitz.movie.presentation.RefreshHandler
import com.fitz.movie.presentation.view.adapter.MoviesListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class AdapterModule {

    @Provides
    fun provideAdapter(fragment: Fragment): MoviesListAdapter {
        fragment as RefreshHandler
        return MoviesListAdapter(
            mutableListOf(),
            fragment.findNavController(),
            fragment
        )
    }
}