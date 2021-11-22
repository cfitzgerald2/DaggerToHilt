package com.fitz.hiltdemo.usecase.dto

import com.fitz.hiltdemo.service.model.MovieResponse
import com.fitz.hiltdemo.usecase.model.MovieViewItem
import javax.inject.Inject

class ServiceMapperDto @Inject constructor(): DomainMapper<MovieViewItem, MovieResponse> {
    override suspend fun mapFromDomain(domainModel: MovieResponse): MovieViewItem {
        return MovieViewItem(
            imageURL = "$baseImagePath${domainModel.posterPath}",
            movieRating = domainModel.voteAverage,
            title = domainModel.title,
            description = domainModel.overview ?: "",
            id = domainModel.idNumber,
            saved = false
        )
    }

    override suspend fun mapToDomainModel(model: MovieViewItem): MovieResponse {
        return MovieResponse()
    }

    companion object {
        private const val baseImagePath = "https://image.tmdb.org/t/p/original"
    }
}