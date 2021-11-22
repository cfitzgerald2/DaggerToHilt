package com.fitz.movie.repository

import com.fitz.movie.persistence.dao.MovieDao
import com.fitz.movie.service.ServiceImplementations
import com.fitz.movie.usecase.dto.DatabaseListMapperDto
import com.fitz.movie.usecase.dto.DatabaseMapperDto
import com.fitz.movie.usecase.dto.ServiceListMapperDto
import com.fitz.movie.usecase.dto.ServiceMapperDto
import io.mockk.coEvery
import io.mockk.mockkClass
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MovieRepositoryTest {

    private lateinit var repository: com.fitz.movie.repository.MovieRepository

    @Before
    fun setUp() {
        val dao = mockkClass(com.fitz.movie.persistence.dao.MovieDao::class)
        coEvery { dao.searchMovieById(any()) } returns null
        repository = com.fitz.movie.repository.MovieRepository(
            com.fitz.movie.service.ServiceImplementations.movieService,
            com.fitz.movie.usecase.dto.ServiceListMapperDto(com.fitz.movie.usecase.dto.ServiceMapperDto()),
            dao,
            com.fitz.movie.usecase.dto.DatabaseListMapperDto(com.fitz.movie.usecase.dto.DatabaseMapperDto()),
            com.fitz.movie.usecase.dto.DatabaseMapperDto()
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun searchMovies() {
        runBlocking {
            val response = repository.searchMovies("veno", 2)
            Assert.assertEquals(20, response.list.size)
        }
    }
}