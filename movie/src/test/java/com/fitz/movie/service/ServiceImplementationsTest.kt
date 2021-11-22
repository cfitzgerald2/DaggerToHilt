package com.fitz.movie.service

import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class ServiceImplementationsTest : TestCase() {

    @Test
    fun testRequestMovies() {
        runBlocking {
            val response = ServiceImplementations.movieService.searchMovieByID( 550)
            Assert.assertEquals(200, response.code())
            Assert.assertEquals(true, response.isSuccessful)
            Assert.assertEquals(63000000, response.body()?.budget)

            val movieList = ServiceImplementations.movieService.findMovies()
            Assert.assertFalse(movieList.body()?.listResults.isNullOrEmpty())
        }
    }

    @Test
    fun testSearchMovies() {
        runBlocking {
            val response = ServiceImplementations.movieService.searchMovies( "veno", 2)
            Assert.assertEquals(200, response.code())
            Assert.assertEquals(true, response.isSuccessful)
            Assert.assertEquals(20, response.body()?.listResults?.size)
        }
    }

    @Test
    fun testSearchMovies_noMoreResults() {
        runBlocking {
            val response = ServiceImplementations.movieService.searchMovies( "veno", 4)
            Assert.assertEquals(200, response.code())
            Assert.assertEquals(true, response.isSuccessful)
            Assert.assertEquals(0, response.body()?.listResults?.size)
        }
    }
}