package com.example.moviesearchapp_mvvm

import android.app.Activity
import com.example.moviesearchapp_mvvm.data.network.MoviesRepositoryImpl
import com.example.moviesearchapp_mvvm.data.network.RetrofitNetworkClient
import com.example.moviesearchapp_mvvm.domain.api.MoviesInteractor
import com.example.moviesearchapp_mvvm.domain.api.MoviesRepository
import com.example.moviesearchapp_mvvm.domain.impl.MoviesInteractorImpl
import com.example.moviesearchapp_mvvm.ui.movies.MoviesAdapter
import com.example.moviesearchapp_mvvm.ui.movies.MoviesSearchController
import com.example.moviesearchapp_mvvm.ui.poster.PosterController

object Creator {
    private fun getMoviesRepository(): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideMoviesInteractor(): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository())
    }

    fun provideMoviesSearchController(activity: Activity, adapter: MoviesAdapter): MoviesSearchController {
        return MoviesSearchController(activity, adapter)
    }

    fun providePosterController(activity: Activity): PosterController {
        return PosterController(activity)
    }
}