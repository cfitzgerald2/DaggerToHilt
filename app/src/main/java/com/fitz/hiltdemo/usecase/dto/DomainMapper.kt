package com.fitz.hiltdemo.usecase.dto

interface DomainMapper<T, DomainModel> {

    suspend fun mapFromDomain(domainModel: DomainModel): T

    suspend fun mapToDomainModel(model: T): DomainModel
}