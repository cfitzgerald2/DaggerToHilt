package com.fitz.movie.repository

import com.fitz.movie.R
import com.fitz.movie.persistence.dao.MovieDao
import com.fitz.movie.service.MovieService
import com.fitz.movie.service.model.MovieListResponse
import com.fitz.movie.usecase.dto.DatabaseListMapperDto
import com.fitz.movie.usecase.dto.DatabaseMapperDto
import com.fitz.movie.usecase.dto.ServiceListMapperDto
import com.fitz.movie.usecase.model.MovieResult
import com.fitz.movie.usecase.model.MovieViewItem
import com.fitz.movie.usecase.model.RepositoryResult
import retrofit2.Response
import timber.log.Timber
import java.lang.IllegalArgumentException
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieService: MovieService,
    private val movieServiceDto: ServiceListMapperDto,
    private val movieDao: MovieDao,
    private val movieDatabaseDto: DatabaseListMapperDto,
    private val movieDatabaseItemDto: DatabaseMapperDto,
) {

    private val errorState = MovieResult(mutableListOf(), RepositoryResult.DataOperation.ERROR)

    private var cachedResponse: MovieResult = errorState

    suspend fun editMovie(movieViewItem: MovieViewItem) {
        val domainModel = movieDatabaseItemDto.mapToDomainModel(movieViewItem)
        movieDao.addMovie(domainModel)
    }

    suspend fun deleteMovie(movieViewItem: MovieViewItem) {
        val domainModel = movieDatabaseItemDto.mapToDomainModel(movieViewItem)
        movieDao.deleteMovie(domainModel)
    }

    suspend fun searchMovies(searchString: String, page: Int = 1): MovieResult {
        val serverResponse = searchMoviesFromServer(searchString, page = page).updateLocallySavedItems()
        cachedResponse = when(page) {
            1 -> {
                serverResponse
            }
            else -> {
                when(serverResponse.dataState) {
                    RepositoryResult.DataOperation.NO_MORE_DATA -> {
                        MovieResult(
                            cachedResponse.list,
                            RepositoryResult.DataOperation.NO_MORE_DATA,
                            errorMessage = R.string.no_more_data_error_string
                        )
                    }
                    RepositoryResult.DataOperation.LOADED,
                    RepositoryResult.DataOperation.ADDED -> {
                        val newList = cachedResponse.list
                        newList += serverResponse.list
                        MovieResult(
                            newList,
                            RepositoryResult.DataOperation.ADDED
                        )
                    }
                    RepositoryResult.DataOperation.ERROR -> {
                        MovieResult(
                            cachedResponse.list,
                            RepositoryResult.DataOperation.ERROR,
                            errorMessage = R.string.loading_error_string
                        )
                    }
                    else -> {
                        throw(IllegalArgumentException("Unexpected return type from server"))
                    }
                }
            }
        }
        return cachedResponse
    }

    suspend fun getMovies(page: Int = 1): MovieResult {
        val serverResponse = getMoviesFromServer(page = page).updateLocallySavedItems()
        cachedResponse = when(page) {
            1 -> {
                serverResponse
            }
            else -> {
                when(serverResponse.dataState) {
                    RepositoryResult.DataOperation.NO_MORE_DATA -> {
                        MovieResult(
                            cachedResponse.list,
                            RepositoryResult.DataOperation.NO_MORE_DATA,
                            errorMessage = R.string.no_more_data_error_string
                        )
                    }
                    RepositoryResult.DataOperation.LOADED,
                    RepositoryResult.DataOperation.ADDED -> {
                        val newList = cachedResponse.list
                        newList += serverResponse.list
                        MovieResult(
                            newList,
                            RepositoryResult.DataOperation.ADDED
                        )
                    }
                    RepositoryResult.DataOperation.ERROR -> {
                        MovieResult(
                            cachedResponse.list,
                            RepositoryResult.DataOperation.ERROR
                        )
                    }
                    else -> {
                        throw(IllegalArgumentException("Unexpected return type from server"))
                    }
                }
            }
        }
        return cachedResponse
    }

    private suspend fun searchMoviesFromServer(searchString: String, page: Int = 1): MovieResult {
        return movieService.searchMovies(searchString, page = page).handleServerResponse()
    }

    private suspend fun getMoviesFromServer(page: Int = 1): MovieResult {
        return movieService.findMovies(page = page).handleServerResponse()
    }

    private suspend fun MovieResult.updateLocallySavedItems(): MovieResult {
        val savedResponses = mutableListOf<Pair<Int, MovieViewItem>>()
        val serverResponse = this
        serverResponse.list.forEachIndexed { index, item ->
            val localSaved = movieDao.searchMovieById(item.id)
            if (localSaved != null) {
                val newItem = movieDatabaseItemDto.mapFromDomain(localSaved)
                savedResponses.add(Pair(index, newItem))
            }
        }
        savedResponses.forEach {
            serverResponse.list.removeAt(it.first)
            serverResponse.list.add(it.first, it.second)
        }
        return serverResponse
    }

    private suspend fun Response<MovieListResponse>.handleServerResponse(): MovieResult {
        val moviesResponse = this
        val results = moviesResponse.body()?.let {
            movieServiceDto.mapFromDomain(it)
        } ?: mutableListOf()
        return when {
            results.isEmpty() -> {
                MovieResult(
                    mutableListOf(),
                    RepositoryResult.DataOperation.NO_MORE_DATA,
                    errorMessage = R.string.no_more_data_error_string
                )
            }
            moviesResponse.code() in 200..399 -> {
                MovieResult(
                    results,
                    RepositoryResult.DataOperation.LOADED
                )
            }
            moviesResponse.code() >= 400 -> {
                Timber.d("errorCode: ${moviesResponse.code()}")
                val errorMessage = moviesResponse.errorBody()?.byteStream()?.bufferedReader()?.readLines()?.joinToString(separator = "\n")
                Timber.d(errorMessage)

                MovieResult(
                    mutableListOf(),
                    RepositoryResult.DataOperation.ERROR,
                    errorMessage = R.string.loading_error_string
                )
            }
            else -> {
                val errorMessage = moviesResponse.errorBody()?.byteStream()?.bufferedReader()?.readLines()?.joinToString(separator = "\n")
                    ?: "Something went wrong with processing this data"
                throw IllegalArgumentException(errorMessage)
            }
        }
    }

    suspend fun getMoviesFromDatabase(): MovieResult {
        val list = try {
            movieDatabaseDto.mapFromDomain(movieDao.getMovies())
        } catch(e: IllegalArgumentException) {
            mutableListOf()
        }
        return if (list.isEmpty()) {
            MovieResult(
                list,
                RepositoryResult.DataOperation.ERROR
            )
        } else {
            MovieResult(
                list,
                RepositoryResult.DataOperation.LOADED
            )
        }
    }

    private suspend fun searchMoviesFromDatabase(searchString: String): MovieResult {
        val list = try {
            movieDatabaseDto.mapFromDomain(movieDao.searchMovieByName(searchString))
        } catch(e: IllegalArgumentException) {
            mutableListOf()
        }
        return if (list.isEmpty()) {
            MovieResult(
                list,
                RepositoryResult.DataOperation.ERROR
            )
        } else {
            MovieResult(
                list,
                RepositoryResult.DataOperation.LOADED
            )
        }
    }
}