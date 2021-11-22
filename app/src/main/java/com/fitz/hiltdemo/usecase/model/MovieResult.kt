package com.fitz.hiltdemo.usecase.model

import androidx.annotation.StringRes

class MovieResult(
    list: MutableList<MovieViewItem>,
    dataState: DataOperation,
    val editedIndex: Int = -1,
    @StringRes var errorMessage: Int = 0
) : RepositoryResult<MovieViewItem>(list, dataState) {

    override fun toString(): String {
        return dataState.name + ": list: ${list.size} ->" + list.joinToString(", ") { it.title }
    }
}