package com.fitz.movie

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.fitz.movie.di.module.DataModule
import com.fitz.movie.usecase.databridge.DataBridge
import com.fitz.movie.usecase.model.FilterArgument
import com.fitz.movie.usecase.model.MovieResult
import com.fitz.movie.usecase.model.MovieViewItem
import com.fitz.movie.usecase.model.RepositoryResult
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
class MockDataModule {

    @Provides
    fun provideDataBridge(@ApplicationContext context: Context): DataBridge<MovieViewItem> {
        return object : DataBridge<MovieViewItem> {
            override suspend fun getData(page: Int): RepositoryResult<MovieViewItem> {
                return MovieResult(mutableListOf(), RepositoryResult.DataOperation.LOADED)
            }

            override suspend fun editEntity(entity: MovieViewItem) {
                withContext(Dispatchers.Main) {
                    val toast = Toast.makeText(context, "Movie entity (${entity.title}) edited", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
            }

            override suspend fun filterData(flags: List<FilterArgument>): RepositoryResult<MovieViewItem> {
                return MovieResult(mutableListOf(), RepositoryResult.DataOperation.NO_MORE_DATA)
            }
        }
    }
}