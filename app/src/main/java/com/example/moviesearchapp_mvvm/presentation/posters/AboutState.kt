package com.example.moviesearchapp_mvvm.presentation.posters

import com.example.moviesearchapp_mvvm.domain.models.MovieDetails

sealed interface AboutState {

    data class Content(
        val movie: MovieDetails
    ) : AboutState

    data class Error(
        val message: String
    ) : AboutState

}