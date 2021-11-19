package com.fitz.hiltdemo.service

import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class MovieServiceImplTest : TestCase() {

    @Test
    fun testRequestMovies() {
        runBlocking {
            val response = MovieServiceImpl.requestMovies()
            Assert.assertEquals(200, response.code())
        }
    }
}