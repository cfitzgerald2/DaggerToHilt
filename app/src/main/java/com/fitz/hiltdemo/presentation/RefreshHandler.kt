package com.fitz.hiltdemo.presentation

/**
 * handle requests to reload data or load more data
 */
interface RefreshHandler {
    fun requestMoreData()
}