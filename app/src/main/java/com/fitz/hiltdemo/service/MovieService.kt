package com.fitz.hiltdemo.service

import com.fitz.hiltdemo.service.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

interface MovieService {

    @GET(value = "/3/movie/550")
    suspend fun searchMovies(): Response<MovieResponse>

    //https://api.themoviedb.org/3/movie/550?api_key=6ca7fe3dc3588613e8b06b051d943c5a
    @GET
    suspend fun searchMovie(
        @Url url: String
    ): Response<MovieResponse>
}