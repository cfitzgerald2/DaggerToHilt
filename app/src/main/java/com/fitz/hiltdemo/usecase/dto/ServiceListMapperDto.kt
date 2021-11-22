package com.fitz.hiltdemo.usecase.dto

import com.fitz.hiltdemo.service.model.MovieListResponse
import com.fitz.hiltdemo.usecase.model.MovieViewItem
import javax.inject.Inject

class ServiceListMapperDto @Inject constructor(private val serviceMapperDto: ServiceMapperDto): DomainMapper<MutableList<MovieViewItem>, MovieListResponse> {
    override suspend fun mapFromDomain(domainModel: MovieListResponse): MutableList<MovieViewItem> {
        val returnList = mutableListOf<MovieViewItem>()
        return domainModel.listResults.mapTo(returnList) {
            serviceMapperDto.mapFromDomain(it)
        }
    }

    override suspend fun mapToDomainModel(model: MutableList<MovieViewItem>): MovieListResponse {
        return MovieListResponse()
    }
}