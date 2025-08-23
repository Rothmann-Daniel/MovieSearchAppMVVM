package com.example.moviesearchapp_mvvm.domain.impl

import com.example.moviesearchapp_mvvm.domain.api.MoviesInteractor
import com.example.moviesearchapp_mvvm.domain.api.MoviesRepository
import java.util.concurrent.Executors

class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchMovies(expression: String, consumer: MoviesInteractor.MoviesConsumer) {
        executor.execute {
            try {
                val movies = repository.searchMovies(expression)
                consumer.consume(movies, null) // Успех: передаем movies и null для errorMessage
            } catch (e: Exception) {
                consumer.consume(null, e.message) // Ошибка: передаем null для movies и сообщение об ошибке
            }
        }
    }
}