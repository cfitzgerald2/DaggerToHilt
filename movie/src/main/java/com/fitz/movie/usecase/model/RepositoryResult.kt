package com.fitz.movie.usecase.model

abstract class RepositoryResult<Entity>(val list: MutableList<Entity>, val dataState: DataOperation) {

    fun isSuccessState(): Boolean {
        return dataState != DataOperation.ERROR && dataState != DataOperation.NO_MORE_DATA
    }

    enum class DataOperation {
        LOADED,
        NO_MORE_DATA,
        ERROR,
        ADDED,
        DELETED,
        EDITED,
        SORTED
    }
}