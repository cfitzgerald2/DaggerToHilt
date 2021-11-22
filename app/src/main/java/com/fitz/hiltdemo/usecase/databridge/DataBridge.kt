package com.fitz.hiltdemo.usecase.databridge

import com.fitz.hiltdemo.usecase.model.RepositoryResult

interface DataBridge<Entity> {

    suspend fun getData(page: Int): RepositoryResult<Entity>

    suspend fun editEntity(entity: Entity)

    suspend fun searchData(searchString: String, page: Int): RepositoryResult<Entity>
}