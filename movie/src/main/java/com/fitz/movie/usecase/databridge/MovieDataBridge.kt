package com.fitz.movie.usecase.databridge

import com.fitz.movie.repository.MovieRepository
import com.fitz.movie.usecase.model.*
import javax.inject.Inject

class MovieDataBridge @Inject constructor(private val repository: MovieRepository):
    DataBridge<MovieViewItem> {

    override suspend fun getData(page: Int): MovieResult {
        return repository.getMovies(page = page)
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