package com.fitz.hiltdemo.service

import com.fitz.hiltdemo.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

/**
 * object to hold retrofit instance and service implementations
 *
 * adds the api_key to each call
 */
object ServiceImplementations {

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor {
            val originalRequest = it.request()
            val newUrl = originalRequest.url().newBuilder().addQueryParameter(
                "api_key",
                BuildConfig.movieDBApiKey
            ).build()
            val newRequest = originalRequest.newBuilder().url(newUrl).build()
            Timber.d("requestURL: ${newRequest.url()}")
            it.proceed(newRequest)
        }.build()
    }

    private val retrofitInstance by lazy {
        Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val movieService: MovieService by lazy {
        retrofitInstance.create(MovieService::class.java)
    }
}