package com.example.moviesearchapp_mvvm.presentation.cast

import com.example.moviesearchapp_mvvm.domain.models.MovieCast

sealed interface MoviesCastState {

    object Loading : MoviesCastState

    // Вместо объекта MovieCast появились два поля
    data class Content(
        val fullTitle: String,
        val items: List<MoviesCastRVItem>,
    ) : MoviesCastState

    data class Error(
        val message: String,
    ) : MoviesCastState

}