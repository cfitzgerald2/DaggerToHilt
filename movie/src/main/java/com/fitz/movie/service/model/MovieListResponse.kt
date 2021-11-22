package com.fitz.movie.service.model

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("page") var currentPage: Int = 0,
    @SerializedName("results") var listResults: List<MovieResponse> = listOf(),
    @SerializedName("total_pages") var totalPages: Int = 0,
    @SerializedName("total_results") var totalResults: Int = 0
) {
}