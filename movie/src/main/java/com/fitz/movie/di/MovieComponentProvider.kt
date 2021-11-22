package com.fitz.movie.di

import com.fitz.movie.di.component.MovieComponent

interface MovieComponentProvider {

    val movieComponent: MovieComponent
}