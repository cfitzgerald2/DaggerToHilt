package com.fitz.movie.usecase.databridge

import com.fitz.movie.usecase.model.FilterArgument
import com.fitz.movie.usecase.model.RepositoryResult

interface DataBridge<Entity> {

    suspend fun getData(page: Int): RepositoryResult<Entity>

    suspend fun editEntity(entity: Entity)

    suspend fun filterData(flags: List<FilterArgument>): RepositoryResult<Entity>
}