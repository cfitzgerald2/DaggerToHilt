package com.fitz.hiltdemo.usecase.dto

interface DomainMapper<T, DomainModel> {

    fun mapFromDomain(domainModel: DomainModel): T

    fun mapToDomainModel(model: T): DomainModel
}