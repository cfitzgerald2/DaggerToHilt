package com.fitz.hiltdemo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.fitz.hiltdemo.usecase.databridge.DataBridge
import com.fitz.hiltdemo.usecase.model.MovieViewItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SecondFragmentViewModel @Inject constructor(
    private val dataBridge: DataBridge<MovieViewItem>,
    private val backgroundDispatcher: CoroutineScope
): ViewModel() {

    fun saveMovie(movieViewItem: MovieViewItem) {
        backgroundDispatcher.launch { saveMovieAsync(movieViewItem) }
    }

    private suspend fun saveMovieAsync(movieViewItem: MovieViewItem) {
        dataBridge.editEntity(movieViewItem)
    }
}