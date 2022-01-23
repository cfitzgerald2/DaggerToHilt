package com.fitz.movie.usecase.databridge

import com.fitz.movie.R
import com.fitz.movie.repository.MovieRepository
import com.fitz.movie.usecase.model.*
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class MovieDataBridge @Inject constructor(private val repository: MovieRepository):
    DataBridge<MovieViewItem> {

    override suspend fun getData(page: Int): MovieResult {
        return try {
            repository.getMovies(page = page)
        } catch (e: IOException) {
            Timber.e(e, "error while getting movies")
            MovieResult(dataState = RepositoryResult.DataOperation.ERROR, list = mutableListOf(), errorMessage = R.string.loading_error_string)
        }
    }

    override suspend fun editEntity(entity: MovieViewItem) {
        if(entity.saved) {
            repository.editMovie(entity)
        } else {
            repository.deleteMovie(entity)
        }
    }

    override suspend fun filterData(flags: List<FilterArgument>): MovieResult {
        // todo handle multiple flags. currently only handling the first
        return when(val flag = flags.firstOrNull()) {
            is LocalSourceFilter -> { repository.getMoviesFromDatabase() }
            is SearchFilter -> { repository.searchMovies(flag.searchString, flag.page)}
            null -> { throw IllegalArgumentException("Flags must not be null when filtering") }
        }
    }
}