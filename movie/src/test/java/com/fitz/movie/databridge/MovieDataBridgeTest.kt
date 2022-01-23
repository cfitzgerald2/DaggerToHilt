package com.fitz.movie.databridge

import com.fitz.movie.persistence.dao.MovieDao
import com.fitz.movie.repository.MovieRepository
import com.fitz.movie.service.ServiceImplementations
import com.fitz.movie.usecase.dto.DatabaseListMapperDto
import com.fitz.movie.usecase.dto.DatabaseMapperDto
import com.fitz.movie.usecase.dto.ServiceListMapperDto
import com.fitz.movie.usecase.dto.ServiceMapperDto
import com.fitz.movie.usecase.model.SearchFilter
import io.mockk.coEvery
import io.mockk.mockkClass
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MovieDataBridgeTest {

    private lateinit var dataBridge: com.fitz.movie.usecase.databridge.MovieDataBridge

    @Before
    fun setUp() {
        val dao = mockkClass(MovieDao::class)
        coEvery { dao.searchMovieById(any()) } returns null
        val repository = MovieRepository(
            ServiceImplementations.movieService,
            ServiceListMapperDto(ServiceMapperDto()),
            dao,
            DatabaseListMapperDto(DatabaseMapperDto()),
            DatabaseMapperDto()
        )
        dataBridge = com.fitz.movie.usecase.databridge.MovieDataBridge(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun searchData() {
        runBlocking {
            val result = dataBridge.filterData(listOf(SearchFilter("veno", 2)))
            Assert.assertEquals(20, result.list.size)
        }
    }
}