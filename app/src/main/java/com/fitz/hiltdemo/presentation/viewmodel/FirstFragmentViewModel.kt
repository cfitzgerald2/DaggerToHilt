package com.fitz.hiltdemo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fitz.hiltdemo.usecase.databridge.DataBridge
import com.fitz.hiltdemo.usecase.model.MovieResult
import com.fitz.hiltdemo.usecase.model.MovieViewItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class FirstFragmentViewModel @Inject constructor(
    dataBridge: DataBridge<MovieViewItem>,
    backgroundDispatcher: CoroutineScope
): ViewModel() {

    private val _moviesListLiveData: MutableLiveData<MovieResult> = MutableLiveData()
    val moviesListLiveData: LiveData<MovieResult> = _moviesListLiveData

    init {
        backgroundDispatcher.launch {
            delay(4000)
            _moviesListLiveData.postValue(dataBridge.getData() as MovieResult)

        }
    }
}