package com.fitz.movie.usecase.databridge

import com.fitz.movie.usecase.model.RepositoryResult

interface DataBridge<Entity> {

    suspend fun getData(page: Int): RepositoryResult<Entity>

    suspend fun editEntity(entity: Entity)

    suspend fun searchData(searchString: String, page: Int): RepositoryResult<Entity>
}