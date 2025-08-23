package com.example.moviesearchapp_mvvm.domain.api

import com.example.moviesearchapp_mvvm.ui.movies.Movie

interface MoviesRepository {
    fun searchMovies(expression: String): List<Movie>
}