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

    private lateinit var repository: MovieRepository

    @Before
    fun setUp() {
        val dao = mockkClass(MovieDao::class)
        coEvery { dao.searchMovieById(any()) } returns null
        repository = MovieRepository(
            ServiceImplementations.movieService,
            ServiceListMapperDto(ServiceMapperDto()),
            dao,
            DatabaseListMapperDto(DatabaseMapperDto()),
            DatabaseMapperDto()
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