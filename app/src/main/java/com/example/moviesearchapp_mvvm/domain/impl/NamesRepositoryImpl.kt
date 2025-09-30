package com.example.moviesearchapp_mvvm.domain.impl

import com.example.moviesearchapp_mvvm.data.dto.NamesSearchRequest
import com.example.moviesearchapp_mvvm.data.dto.NamesSearchResponse
import com.example.moviesearchapp_mvvm.data.network.NetworkClient
import com.example.moviesearchapp_mvvm.domain.api.NamesRepository
import com.example.moviesearchapp_mvvm.domain.models.Person
import com.example.moviesearchapp_mvvm.util.Resource

class NamesRepositoryImpl(private val networkClient: NetworkClient) : NamesRepository {

    override fun searchNames(expression: String): Resource<List<Person>> {
        val response = networkClient.doRequest(NamesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                with(response as NamesSearchResponse) {
                    Resource.Success(results.map {
                        Person(id = it.id,
                            name = it.title,
                            description = it.description,
                            photoUrl = it.image)
                    })
                }
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }
}