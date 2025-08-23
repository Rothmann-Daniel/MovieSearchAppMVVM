package com.example.moviesearchapp_mvvm.presentation.cast

import com.example.moviesearchapp_mvvm.core.ui.RVItem
import com.example.moviesearchapp_mvvm.domain.models.MovieCastPerson

sealed interface MoviesCastRVItem: RVItem {

    data class HeaderItem(
        val headerText: String,
    ) : MoviesCastRVItem

    data class PersonItem(
        val data: MovieCastPerson,
    ) : MoviesCastRVItem

}