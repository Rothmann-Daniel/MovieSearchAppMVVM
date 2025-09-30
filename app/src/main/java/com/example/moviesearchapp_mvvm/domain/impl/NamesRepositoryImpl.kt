package com.example.moviesearchapp_mvvm.domain.impl

import com.example.moviesearchapp_mvvm.data.dto.NamesSearchRequest
import com.example.moviesearchapp_mvvm.data.dto.NamesSearchResponse
import com.example.moviesearchapp_mvvm.data.network.NetworkClient
import com.example.moviesearchapp_mvvm.domain.api.NamesRepository
import com.example.moviesearchapp_mvvm.domain.models.Person
import com.example.moviesearchapp_mvvm.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NamesRepositoryImpl(private val networkClient: NetworkClient) : NamesRepository {

    override fun searchNames(expression: String): Flow<Resource<List<Person>>> = flow {
        try {
            val response = networkClient.doRequest(NamesSearchRequest(expression))
            when (response.resultCode) {
                -1 -> {
                    emit(Resource.Error("Проверьте подключение к интернету"))
                }
                200 -> {
                    with(response as NamesSearchResponse) {
                        val data = results.map { personDto ->
                            Person(
                                id = personDto.id,
                                name = personDto.title,
                                description = personDto.description,
                                photoUrl = personDto.image
                            )
                        }
                        emit(Resource.Success(data))
                    }
                }
                else -> {
                    emit(Resource.Error("Ошибка сервера"))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error("Ошибка сети: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO) // Добавьте эту строку
}