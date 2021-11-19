package com.fitz.hiltdemo.usecase.model

class MovieResult(
    list: MutableList<MovieViewItem>,
    dataState: DataOperation,
    val editedIndex: Int = -1
) : RepositoryResult<MovieViewItem>(list, dataState)