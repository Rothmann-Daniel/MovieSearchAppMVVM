package com.example.moviesearchapp_mvvm.data.network

import com.example.moviesearchapp_mvvm.ui.movies.Movie
import com.example.moviesearchapp_mvvm.data.dto.MoviesSearchRequest
import com.example.moviesearchapp_mvvm.data.dto.MoviesSearchResponse
import com.example.moviesearchapp_mvvm.domain.api.MoviesRepository

class MoviesRepositoryImpl(private val networkClient: NetworkClient) : MoviesRepository {

    override fun searchMovies(expression: String): List<Movie> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        if (response.resultCode == 200) {
            return (response as MoviesSearchResponse).results.map {
                Movie(it.id, it.resultType, it.image, it.title, it.description) }
        } else {
            return emptyList()
        }
    }
}