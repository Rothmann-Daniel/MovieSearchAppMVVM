package com.example.moviesearchapp_mvvm.data.network

import com.example.moviesearchapp_mvvm.data.dto.MoviesSearchRequest
import com.example.moviesearchapp_mvvm.data.dto.Response

class RetrofitNetworkClient(
    private val imdbService: IMDbApiService
) : NetworkClient {

    override fun doRequest(dto: Any): Response {
        if (dto is MoviesSearchRequest) {
            val resp = imdbService.searchMovies(dto.expression).execute()

            val body = resp.body() ?: Response()

            return body.apply { resultCode = resp.code() }
        } else {
            return Response().apply { resultCode = 400 }
        }
    }
}