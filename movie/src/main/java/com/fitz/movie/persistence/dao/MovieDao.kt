package com.fitz.movie.persistence.dao

import androidx.room.*
import com.fitz.movie.persistence.model.MovieEntity

// accessing movie info from database
@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE title = :name")
    suspend fun searchMovieByName(name: String): List<MovieEntity>

    @Query("SELECT DISTINCT * FROM movies WHERE id = :id")
    suspend fun searchMovieById(id: Int): MovieEntity?

    @Query("SELECT * FROM movies")
    suspend fun getMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: MovieEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)
}