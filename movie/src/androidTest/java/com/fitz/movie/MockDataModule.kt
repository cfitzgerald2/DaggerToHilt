package com.fitz.movie

import com.fitz.movie.di.module.DataModule
import com.fitz.movie.usecase.databridge.DataBridge
import com.fitz.movie.usecase.model.FilterArgument
import com.fitz.movie.usecase.model.MovieResult
import com.fitz.movie.usecase.model.MovieViewItem
import com.fitz.movie.usecase.model.RepositoryResult
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
class MockDataModule {

    @Provides
    fun provideDataBridge(): DataBridge<MovieViewItem> {
        return object : DataBridge<MovieViewItem> {
            override suspend fun getData(page: Int): RepositoryResult<MovieViewItem> {
                return MovieResult(mutableListOf(), RepositoryResult.DataOperation.LOADED)
            }

            override suspend fun editEntity(entity: MovieViewItem) {}

            override suspend fun filterData(flags: List<FilterArgument>): RepositoryResult<MovieViewItem> {
                return MovieResult(mutableListOf(), RepositoryResult.DataOperation.NO_MORE_DATA)
            }

        }
    }
}