package com.example.moviesearchapp_mvvm.di

import android.content.Context
import com.example.moviesearchapp_mvvm.data.network.IMDbApiService
import com.example.moviesearchapp_mvvm.data.network.MoviesRepositoryImpl
import com.example.moviesearchapp_mvvm.data.network.NetworkClient
import com.example.moviesearchapp_mvvm.data.network.RetrofitNetworkClient
import com.example.moviesearchapp_mvvm.domain.api.MoviesInteractor
import com.example.moviesearchapp_mvvm.domain.api.MoviesRepository
import com.example.moviesearchapp_mvvm.domain.impl.MoviesInteractorImpl
import com.example.moviesearchapp_mvvm.presentation.movies.MoviesViewModel
import com.example.moviesearchapp_mvvm.presentation.posters.AboutViewModel
import com.example.moviesearchapp_mvvm.presentation.posters.PosterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://tv-api.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<IMDbApiService> {
        get<Retrofit>().create(IMDbApiService::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(
            context = androidContext(),
            imdbService = get()
        )
    }
}
val repositoryModule = module {
    single<MoviesRepository> { MoviesRepositoryImpl(get()) }
}

val interactorModule = module {
    single<MoviesInteractor> { MoviesInteractorImpl(get()) }
}

val viewModelModule = module {
    viewModel { (context: Context) ->
        MoviesViewModel(
            context = context,
            moviesInteractor = get()
        )
    }

    viewModel { (posterUrl: String) ->
        PosterViewModel(posterUrl)
    }

    viewModel {
        MoviesViewModel(androidContext(), get())
    }

    viewModel {(movieId: String) ->
        AboutViewModel(movieId, get())
    }


}