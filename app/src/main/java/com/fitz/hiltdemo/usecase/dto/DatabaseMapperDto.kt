package com.fitz.hiltdemo.usecase.dto

import com.fitz.hiltdemo.persistence.model.MovieEntity
import com.fitz.hiltdemo.usecase.model.MovieViewItem
import javax.inject.Inject

class DatabaseMapperDto @Inject constructor(): DomainMapper<MovieViewItem, MovieEntity> {
    override fun mapFromDomain(domainModel: MovieEntity): MovieViewItem {
        return MovieViewItem(
            title = domainModel.title,
            imageURL = domainModel.imageURL,
            movieRating = domainModel.movieRating,
        )
    }

    override fun mapToDomainModel(model: MovieViewItem): MovieEntity {
        return MovieEntity(
            title = model.title,
            imageURL = model.imageURL,
            movieRating = model.movieRating
        )
    }
}