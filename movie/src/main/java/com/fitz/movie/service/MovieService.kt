package com.fitz.movie.service

import com.fitz.movie.service.model.MovieListResponse
import com.fitz.movie.service.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Fetching data from MovieDb
 */
interface MovieService {

    @GET(value = "/3/movie/{movie_id}")
    suspend fun searchMovieByID(
        @Path("movie_id") movieID: Int
    ): Response<MovieResponse>

    @GET(value = "3/discover/movie")
    suspend fun findMovies(
        @Query("sort_by") sorting: String = "popularity.desc",
        @Query("page") page: Int = 1,
        @Query("language") languageKey: String = "en-US"
    ): Response<MovieListResponse>

    @GET(value = "3/search/movie")
    suspend fun searchMovies(
        @Query("query") searchString: String = "popularity.desc",
        @Query("page") page: Int = 1,
        @Query("language") languageKey: String = "en-US"
    ): Response<MovieListResponse>
}