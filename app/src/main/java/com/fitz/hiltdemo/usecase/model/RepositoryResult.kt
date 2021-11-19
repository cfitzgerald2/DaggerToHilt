package com.fitz.hiltdemo.usecase.model

abstract class RepositoryResult<Entity>(val list: MutableList<Entity>, val dataState: DataOperation) {

    enum class DataOperation {
        LOADED,
        ERROR,
        ADDED,
        DELETED,
        EDITED,
        SORTED
    }
}