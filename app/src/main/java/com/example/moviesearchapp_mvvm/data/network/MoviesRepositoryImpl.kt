package com.example.moviesearchapp_mvvm.data.network

import com.example.moviesearchapp_mvvm.data.converters.MovieCastConverter
import com.example.moviesearchapp_mvvm.data.dto.MovieCastRequest
import com.example.moviesearchapp_mvvm.data.dto.MovieCastResponse
import com.example.moviesearchapp_mvvm.util.Resource
import com.example.moviesearchapp_mvvm.data.dto.MovieDetailsRequest
import com.example.moviesearchapp_mvvm.data.dto.MovieDetailsResponse
import com.example.moviesearchapp_mvvm.domain.models.Movie
import com.example.moviesearchapp_mvvm.data.dto.MoviesSearchRequest
import com.example.moviesearchapp_mvvm.data.dto.MoviesSearchResponse
import com.example.moviesearchapp_mvvm.domain.api.MoviesRepository
import com.example.moviesearchapp_mvvm.domain.models.MovieCast
import com.example.moviesearchapp_mvvm.domain.models.MovieCastPerson
import com.example.moviesearchapp_mvvm.domain.models.MovieDetails

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val movieCastConverter: MovieCastConverter,
) : MoviesRepository {

    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            200 -> {
                with(response as MoviesSearchResponse) {
                    Resource.Success(results.map {
                        Movie(it.id, it.resultType, it.image, it.title, it.description)
                    })
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


    // Добавили новый метод для получения состава участников
    override fun getMovieCast(movieId: String): Resource<MovieCast> {
        val response = networkClient.doRequest(MovieCastRequest(movieId))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                // используем конвертер вместо прямой конвертации
                Resource.Success(
                    data = movieCastConverter.convert(response as MovieCastResponse)
                )
            }
            else -> {
                Resource.Error("Ошибка сервера")

            }
        }
    }
}