package com.fitz.movie.presentation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.fitz.movie.R
import com.fitz.movie.usecase.databridge.DataBridge
import com.fitz.movie.usecase.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class FirstFragmentViewModel @Inject constructor(
    private val dataBridge: DataBridge<MovieViewItem>,
    private val backgroundDispatcher: CoroutineScope
): ViewModel() {

    private val _moviesListLiveData: MutableLiveData<MovieResult> = MutableLiveData()
    val moviesListLiveData: LiveData<MovieResult> = _moviesListLiveData
    val scrollToTopLiveData = object: MutableLiveData<Boolean>() {
        override fun observe(owner: LifecycleOwner, observer: Observer<in Boolean>) {
            this.value = false
            super.observe(owner, observer)
        }
    }

    @Volatile
    var page = 1
    private val throttleDelay = 2000L
    private var disableRemoteSearch: Boolean = false

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

    fun clearAllFilters() {
        disableRemoteSearch = false
        page = 1
        requestMoreData()
    }

    fun getSavedMovies() {
        disableRemoteSearch = true
        backgroundDispatcher.launch {
            _moviesListLiveData.postValue(dataBridge.filterData(listOf(LocalSourceFilter())) as MovieResult)
        }
    }

    fun requestMoreData() {
        backgroundDispatcher.launch {
            if(searchString.isNotBlank()) {
                searchForDataAsync(page++)
            } else {
                requestMoreDataAsync(page++)
            }
        }
    }

    @StringRes
    fun getDialogTitle(): Int {
        return if(disableRemoteSearch) {
            R.string.clear_filter_title
        } else {
            R.string.filter_title
        }
    }

    @StringRes
    fun getPositiveButton(): Int {
        return if(disableRemoteSearch) {
            R.string.clear_filter_positive_button
        } else {
            R.string.filter_positive_button
        }
    }

    @StringRes
    fun getNegativeButton(): Int {
        return if(disableRemoteSearch) {
            R.string.clear_filter_negative_button
        } else {
            R.string.filter_negative_button
        }
    }

    private suspend fun requestMoreDataAsync(page: Int) {
        if(!disableRemoteSearch) {
            _moviesListLiveData.postValue(dataBridge.getData(page = page) as MovieResult)
        }
    }

    private suspend fun searchForDataAsync(page: Int) {
        if(!disableRemoteSearch) {
            _moviesListLiveData.postValue(
                dataBridge.filterData(
                    listOf(
                        SearchFilter(
                            searchString = searchString,
                            page = page
                        )
                    )
                ) as MovieResult
            )
        }
    }
}