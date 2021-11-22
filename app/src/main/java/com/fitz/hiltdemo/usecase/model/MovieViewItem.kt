package com.fitz.hiltdemo.usecase.model

import java.io.Serializable

data class MovieViewItem(
    val title: String,
    val imageURL: String,
    val movieRating: Float,
    val description: String,
    val id: Int,
    var saved: Boolean
): Serializable {
    companion object {
        const val SELECTED_MOVIE_ITEM = "selectedMovieItemKey"
    }
}