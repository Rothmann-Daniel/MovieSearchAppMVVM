package com.example.moviesearchapp_mvvm.domain.api

import com.example.moviesearchapp_mvvm.domain.models.Movie

import com.example.moviesearchapp_mvvm.domain.models.MovieDetails

interface MoviesInteractor {

    fun searchMovies(query: String, consumer: MoviesConsumer)
    fun getMoviesDetails(movieId: String, consumer: MovieDetailsConsumer) // Добавьте этот метод

    interface MoviesConsumer {
        fun consume(foundMovies: List<Movie>?, errorMessage: String?)
    }

    interface MovieDetailsConsumer { // Добавьте этот интерфейс
        fun consume(movieDetails: MovieDetails?, errorMessage: String?)
    }
}