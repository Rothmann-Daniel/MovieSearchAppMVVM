package com.example.moviesearchapp_mvvm.data.network

import com.example.moviesearchapp_mvvm.data.dto.MovieCastResponse
import com.example.moviesearchapp_mvvm.data.dto.MovieDetailsResponse
import com.example.moviesearchapp_mvvm.data.dto.MoviesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

private const val IMDB_API_KEY = "k_zcuw1ytf"
interface IMDbApiService {
    @GET("/en/API/SearchMovie/$IMDB_API_KEY/{expression}")
    fun searchMovies(@Path("expression") expression: String): Call<MoviesSearchResponse>

    @GET("/en/API/Title/$IMDB_API_KEY/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: String): Call<MovieDetailsResponse>

    @GET("/en/API/FullCast/$IMDB_API_KEY/{movie_id}")
    fun getFullCast(@Path("movie_id") movieId: String): Call<MovieCastResponse>
}
