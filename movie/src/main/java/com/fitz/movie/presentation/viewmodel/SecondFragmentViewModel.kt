package com.fitz.movie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.fitz.movie.usecase.databridge.DataBridge
import com.fitz.movie.usecase.model.MovieViewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecondFragmentViewModel @Inject constructor(
    private val dataBridge: com.fitz.movie.usecase.databridge.DataBridge<com.fitz.movie.usecase.model.MovieViewItem>,
    private val backgroundDispatcher: CoroutineScope
): ViewModel() {

    fun saveMovie(movieViewItem: com.fitz.movie.usecase.model.MovieViewItem) {
        backgroundDispatcher.launch { saveMovieAsync(movieViewItem) }
    }

    private suspend fun saveMovieAsync(movieViewItem: com.fitz.movie.usecase.model.MovieViewItem) {
        dataBridge.editEntity(movieViewItem)
    }
}