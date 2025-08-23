package com.example.moviesearchapp_mvvm.domain.api

import com.example.moviesearchapp_mvvm.ui.movies.Movie

interface MoviesInteractor {
    fun searchMovies(expression: String, consumer: MoviesConsumer)

    interface MoviesConsumer {
        fun consume(foundMovies: List<Movie>)
    }
}