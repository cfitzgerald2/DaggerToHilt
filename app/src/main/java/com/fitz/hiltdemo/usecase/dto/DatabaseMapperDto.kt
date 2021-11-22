package com.fitz.hiltdemo.usecase.dto

import com.fitz.hiltdemo.persistence.model.MovieEntity
import com.fitz.hiltdemo.usecase.model.MovieViewItem
import javax.inject.Inject

class DatabaseMapperDto @Inject constructor(): DomainMapper<MovieViewItem, MovieEntity> {
    override suspend fun mapFromDomain(domainModel: MovieEntity): MovieViewItem {
        return MovieViewItem(
            title = domainModel.title,
            imageURL = domainModel.imageURL,
            movieRating = domainModel.movieRating,
            description = domainModel.description,
            id = domainModel.id,
            saved = true
        )
    }

    override suspend fun mapToDomainModel(model: MovieViewItem): MovieEntity {
        return MovieEntity(
            title = model.title,
            imageURL = model.imageURL,
            movieRating = model.movieRating,
            description = model.description,
            id = model.id
        )
    }
}