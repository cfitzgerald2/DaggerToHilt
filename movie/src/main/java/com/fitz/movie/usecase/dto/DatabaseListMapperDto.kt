package com.fitz.movie.usecase.dto

import com.fitz.movie.persistence.model.MovieEntity
import com.fitz.movie.usecase.model.MovieViewItem
import javax.inject.Inject

class DatabaseListMapperDto @Inject constructor(private val databaseMapperDto: DatabaseMapperDto):
    DomainMapper<MutableList<MovieViewItem>, List<MovieEntity>> {
    override suspend fun mapFromDomain(domainModel: List<MovieEntity>): MutableList<MovieViewItem> {
        val returnList = mutableListOf<MovieViewItem>()
        return domainModel.mapTo(returnList) {
            databaseMapperDto.mapFromDomain(it)
        }
    }

    override suspend fun mapToDomainModel(model: MutableList<MovieViewItem>): List<MovieEntity> {
        return model.map { databaseMapperDto.mapToDomainModel(it) }
    }
}