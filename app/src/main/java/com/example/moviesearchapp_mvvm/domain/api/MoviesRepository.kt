package com.example.moviesearchapp_mvvm.domain.api

import com.example.moviesearchapp_mvvm.Resource
import com.example.moviesearchapp_mvvm.domain.models.Movie
import com.example.moviesearchapp_mvvm.domain.models.MovieDetails

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>
    fun getMovieDetails(movieId: String): Resource<MovieDetails>
}