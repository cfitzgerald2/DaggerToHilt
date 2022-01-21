package com.fitz.movie.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fitz.movie.usecase.databridge.DataBridge
import com.fitz.movie.usecase.model.FilterArgument
import com.fitz.movie.usecase.model.MovieResult
import com.fitz.movie.usecase.model.MovieViewItem
import com.fitz.movie.usecase.model.SearchFilter
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.*

class FirstFragmentViewModelTest {

    @get:Rule val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: FirstFragmentViewModel
    private var searchString = ""
    private var page = 0

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        val mockkDataBridge = mockkClass(DataBridge::class) as DataBridge<MovieViewItem>
        val mockMovieResult = mockkClass(MovieResult::class)
        coEvery { mockkDataBridge.filterData(any()) } answers {
            val searchList: List<FilterArgument> = arg(0)
            val filter: FilterArgument = searchList.first()
            if(filter is SearchFilter) {
                searchString = filter.searchString
                page = filter.page
            }
            mockMovieResult
        }
        val dispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(dispatcher)
        viewModel = FirstFragmentViewModel(
            mockkDataBridge,
            dispatcher
        )
    }

    @ExperimentalCoroutinesApi
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