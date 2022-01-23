package com.fitz.movie.usecase.model

sealed class FilterArgument

class LocalSourceFilter: FilterArgument()

class SearchFilter(val searchString: String, val page: Int): FilterArgument()