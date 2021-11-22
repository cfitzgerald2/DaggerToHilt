package com.fitz.movie.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitz.movie.presentation.RefreshHandler
import com.fitz.movie.usecase.databridge.DataBridge
import com.fitz.movie.usecase.model.MovieResult
import com.fitz.movie.usecase.model.MovieViewItem
import kotlinx.coroutines.*
import javax.inject.Inject

class FirstFragmentViewModel @Inject constructor(
    private val dataBridge: com.fitz.movie.usecase.databridge.DataBridge<com.fitz.movie.usecase.model.MovieViewItem>,
    private val backgroundDispatcher: CoroutineScope
): ViewModel(), RefreshHandler {

    private val _moviesListLiveData: MutableLiveData<com.fitz.movie.usecase.model.MovieResult> = MutableLiveData()
    val moviesListLiveData: LiveData<com.fitz.movie.usecase.model.MovieResult> = _moviesListLiveData
    val scrollToTopLiveData = MutableLiveData<Boolean>()

    @Volatile
    var page = 1
    private val throttleDelay = 2000L

    init {
        requestMoreData()
    }

    @Volatile
    private var blockedByThrottling = false
    @Volatile
    var searchString: String = ""
        private set(value) {
            if(!blockedByThrottling && field != value) {
                field = value
                backgroundDispatcher.launch {
                    throttleSearchAsync()
                }
            } else {
                field = value
            }
        }

    fun searchForData(searchString: String?) {
        searchString?.let {
            if (searchString.length > 2) {
                scrollToTopLiveData.postValue(true)
                this.searchString = searchString
            }
        } ?: run {
            this.searchString = ""
        }
    }

    private suspend fun throttleSearchAsync() {
        var originalSearchString = searchString
        page = 1
        blockedByThrottling = true
        viewModelScope.launch {
            // throttle search and wait for current search to return
            awaitAll(
                async {
                    delay(throttleDelay)
                },
                async {
                    requestMoreData()
                }
            )
            // if search string was updated, continue searching
            if(originalSearchString != searchString) {
                originalSearchString = searchString
                throttleSearchAsync()
            } else {
                blockedByThrottling = false
            }
        }
    }

    override fun requestMoreData() {
        backgroundDispatcher.launch {
            if(searchString.isNotBlank()) {
                searchForDataAsync(page++)
            } else {
                requestMoreDataAsync(page++)
            }
        }
    }

    private suspend fun requestMoreDataAsync(page: Int) {
        _moviesListLiveData.postValue(dataBridge.getData(page = page) as com.fitz.movie.usecase.model.MovieResult)
    }

    private suspend fun searchForDataAsync(page: Int) {
        _moviesListLiveData.postValue(dataBridge.searchData(searchString = searchString, page = page) as com.fitz.movie.usecase.model.MovieResult)
    }
}