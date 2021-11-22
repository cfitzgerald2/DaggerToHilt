package com.fitz.hiltdemo.usecase.databridge

import com.fitz.hiltdemo.persistence.dao.MovieDao
import com.fitz.hiltdemo.repository.MovieRepository
import com.fitz.hiltdemo.service.ServiceImplementations
import com.fitz.hiltdemo.usecase.dto.DatabaseListMapperDto
import com.fitz.hiltdemo.usecase.dto.DatabaseMapperDto
import com.fitz.hiltdemo.usecase.dto.ServiceListMapperDto
import com.fitz.hiltdemo.usecase.dto.ServiceMapperDto
import io.mockk.coEvery
import io.mockk.mockkClass
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MovieDataBridgeTest {

    private lateinit var dataBridge: MovieDataBridge

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
        dataBridge = MovieDataBridge(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun searchData() {
        runBlocking {
            val result = dataBridge.searchData("veno", 2)
            Assert.assertEquals(20, result.list.size)
        }
    }
}