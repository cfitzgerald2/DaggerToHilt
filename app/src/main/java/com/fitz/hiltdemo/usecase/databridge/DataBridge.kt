package com.fitz.hiltdemo.usecase.databridge

import com.fitz.hiltdemo.usecase.model.RepositoryResult

interface DataBridge<Entity> {

    fun getData(): RepositoryResult<Entity>

    fun editEntity(entity: Entity)
}