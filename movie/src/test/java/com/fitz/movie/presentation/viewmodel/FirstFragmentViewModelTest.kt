package com.fitz.movie.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fitz.movie.usecase.databridge.DataBridge
import com.fitz.movie.usecase.model.MovieResult
import com.fitz.movie.usecase.model.MovieViewItem
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.*


class FirstFragmentViewModelTest {

    @get:Rule val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: com.fitz.movie.presentation.viewmodel.FirstFragmentViewModel
    private var searchString = ""
    private var page = 0

    @Before
    fun setUp() {
        val mockkDataBridge = mockkClass(DataBridge::class) as DataBridge<MovieViewItem>
        val mockMovieResult = mockkClass(MovieResult::class)
        coEvery { mockkDataBridge.searchData(any(), any()) } answers {
            searchString = arg(0)
            page = arg(1)
            mockMovieResult
        }
        val dispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(dispatcher)
        viewModel = com.fitz.movie.presentation.viewmodel.FirstFragmentViewModel(
            mockkDataBridge,
            dispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun searchForData() {
        runBlocking {
            viewModel.searchForData(null)
            Assert.assertEquals(0, page)
            viewModel.searchForData("ve")
            Assert.assertEquals(0, page)
            viewModel.searchForData("ven")
            Assert.assertEquals(1, page)
            viewModel.requestMoreData()
            Assert.assertEquals(3, viewModel.page)
        }

    }

    @Test
    fun requestMoreData() {
    }
}