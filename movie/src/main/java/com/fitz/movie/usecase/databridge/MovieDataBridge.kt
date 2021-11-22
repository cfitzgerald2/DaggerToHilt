package com.fitz.movie.usecase.databridge

import com.fitz.movie.repository.MovieRepository
import com.fitz.movie.usecase.model.MovieResult
import com.fitz.movie.usecase.model.MovieViewItem
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

    override suspend fun searchData(searchString: String, page: Int): MovieResult {
        return repository.searchMovies(searchString, page)
    }
}