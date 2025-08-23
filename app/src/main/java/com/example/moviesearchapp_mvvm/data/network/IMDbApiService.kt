package com.example.moviesearchapp_mvvm.data.network

import com.example.moviesearchapp_mvvm.data.dto.MoviesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

private const val IMDB_API_KEY = "k_zcuw1ytf"
interface IMDbApiService {
    @GET("/en/API/SearchMovie/$IMDB_API_KEY/{expression}")
    fun searchMovies(@Path("expression") expression: String): Call<MoviesSearchResponse>
}