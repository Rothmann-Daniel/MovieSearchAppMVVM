package com.example.moviesearchapp_mvvm


import android.content.Context
import com.example.moviesearchapp_mvvm.data.network.MoviesRepositoryImpl
import com.example.moviesearchapp_mvvm.data.network.RetrofitNetworkClient
import com.example.moviesearchapp_mvvm.domain.api.MoviesInteractor
import com.example.moviesearchapp_mvvm.domain.api.MoviesRepository
import com.example.moviesearchapp_mvvm.domain.impl.MoviesInteractorImpl

object Creator {
    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }
}