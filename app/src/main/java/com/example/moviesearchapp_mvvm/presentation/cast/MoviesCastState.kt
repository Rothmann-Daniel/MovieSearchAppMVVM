package com.example.moviesearchapp_mvvm.presentation.cast

import com.example.moviesearchapp_mvvm.core.ui.RVItem
import com.example.moviesearchapp_mvvm.domain.models.MovieCast

sealed interface MoviesCastState {

    object Loading : MoviesCastState

    data class Content(
        val fullTitle: String,
        // Поменяли тип ячеек на более общий
        val items: List<RVItem>,
    ) : MoviesCastState

    data class Error(
        val message: String,
    ) : MoviesCastState

}