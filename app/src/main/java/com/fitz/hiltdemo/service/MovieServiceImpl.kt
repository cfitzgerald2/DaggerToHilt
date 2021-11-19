package com.fitz.hiltdemo.service

import com.fitz.hiltdemo.service.model.MovieResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieServiceImpl {

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor {
            val originalRequest = it.request()
            val newUrl = originalRequest.url().newBuilder().addQueryParameter(
                "api_key",
                "TODO"
            ).build()
            val newRequest = originalRequest.newBuilder().url(newUrl).build()
            println(newRequest.url())
            println(newRequest.body())
            println(newRequest.headers())
            it.proceed(newRequest)
        }.build()
    }

    private val retrofitInstance by lazy {
        Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    private val movieService by lazy {
        retrofitInstance.create(MovieService::class.java)
    }

    suspend fun requestMovies(): Response<MovieResponse> {
        return movieService.searchMovies()
    }
}