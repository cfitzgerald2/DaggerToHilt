package com.fitz.movie.service.model

import com.google.gson.annotations.SerializedName

/**
 * Server response when querying an individual movie
 */
data class MovieResponse(
    var adult: Boolean? = null,
    @SerializedName("backdrop_path") var  backdropPath: String? = null,
    var budget: Int? = null,
    var genres: List<Genre>? = null,
    var homepage: String? = null,
    @SerializedName("id") var idNumber: Int = 0,
    @SerializedName("imdb_id") var imdbID: String? = null,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    var overview: String? = null,
    var popularity: Float? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("production_companies") var productionCompanies: List<ProductionCompany>? = null,
    @SerializedName("production_countries") var productionCountries: List<ProductionCountry>? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    var revenue: Long? = null,
    var runtime: Int? = null,
    @SerializedName("spoken_languages") var spokenLanguages: List<Language>? = null,
    var status: String? = null,
    var tagline: String? = null,
    var title: String = "Untitled",
    var video: Boolean? = null,
    @SerializedName("vote_average") var voteAverage: Float = 0F,
    @SerializedName("vote_count") var voteCount: Long? = null
)

data class Genre(
    @SerializedName("id") var idNumber: Int? = null,
    var name: String? = null
)

data class ProductionCompany(
    var id: Int? = null,
    @SerializedName("logo_path") var logoPath: String? = null,
    var name: String? = null,
    @SerializedName("origin_country") var originCountry: String? = null
)

data class ProductionCountry(
    @SerializedName("iso_3166_1") var countryCode: String? = null,
    var name: String? = null
)

data class Language(
    @SerializedName("english_name") var languageNameInEnglish: String? = null,
    @SerializedName("iso_639_1") var languageCode: String? = null,
    var name: String? = null
)