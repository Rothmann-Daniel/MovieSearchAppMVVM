package com.example.moviesearchapp_mvvm.presentation.cast

import com.example.moviesearchapp_mvvm.domain.models.MovieCastPerson

sealed interface MoviesCastRVItem {

    data class HeaderItem(
        val headerText: String,
    ) : MoviesCastRVItem

    data class PersonItem(
        val data: MovieCastPerson,
    ) : MoviesCastRVItem

}