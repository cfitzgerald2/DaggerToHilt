package com.fitz.movie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitz.movie.usecase.databridge.DataBridge
import com.fitz.movie.usecase.logger.AppLogger
import com.fitz.movie.usecase.logger.Logger
import com.fitz.movie.usecase.model.MovieViewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecondFragmentViewModel @Inject constructor(
    private val dataBridge: DataBridge<MovieViewItem>,
    @AppLogger private val logger: Logger,
    private val backgroundDispatcher: CoroutineDispatcher
): ViewModel() {

    init {
        logger.log("${this::class.java.simpleName} initialized")
    }

    fun saveMovie(movieViewItem: MovieViewItem) {
        viewModelScope.launch(backgroundDispatcher) { saveMovieAsync(movieViewItem) }
    }

    private suspend fun saveMovieAsync(movieViewItem: MovieViewItem) {
        dataBridge.editEntity(movieViewItem)
    }
}