package com.example.moviesearchapp_mvvm.data.network

import com.example.moviesearchapp_mvvm.Resource
import com.example.moviesearchapp_mvvm.data.dto.MovieDetailsRequest
import com.example.moviesearchapp_mvvm.data.dto.MovieDetailsResponse
import com.example.moviesearchapp_mvvm.domain.models.Movie
import com.example.moviesearchapp_mvvm.data.dto.MoviesSearchRequest
import com.example.moviesearchapp_mvvm.data.dto.MoviesSearchResponse
import com.example.moviesearchapp_mvvm.domain.api.MoviesRepository
import com.example.moviesearchapp_mvvm.domain.models.MovieDetails

class MoviesRepositoryImpl(private val networkClient: NetworkClient) : MoviesRepository {

    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                with(response as MoviesSearchResponse) {
                    Resource.Success(results.map {
                        Movie(it.id, it.resultType, it.image, it.title, it.description)})
                }
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    override fun getMovieDetails(movieId: String): Resource<MovieDetails> {
        val response = networkClient.doRequest(MovieDetailsRequest(movieId))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                with(response as MovieDetailsResponse) {
                    Resource.Success(
                        MovieDetails(
                            id = id,
                            title = title,
                            imDbRating = imDbRating,
                            year = year,
                            countries = countries,
                            genres = genres,
                            directors = directors,
                            writers = writers,
                            stars = stars,
                            plot = plot,
                        )
                    )
                }
            }
            else -> {
                Resource.Error("Ошибка сервера")

            }
        }
    }
}