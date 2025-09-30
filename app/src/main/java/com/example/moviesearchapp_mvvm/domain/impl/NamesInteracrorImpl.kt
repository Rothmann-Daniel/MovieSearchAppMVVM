package com.example.moviesearchapp_mvvm.domain.impl

import com.example.moviesearchapp_mvvm.domain.api.NamesInteractor
import com.example.moviesearchapp_mvvm.domain.api.NamesRepository
import com.example.moviesearchapp_mvvm.domain.models.Person
import com.example.moviesearchapp_mvvm.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.Executors

class NamesInteractorImpl(private val repository: NamesRepository) : NamesInteractor {

    override fun searchNames(expression: String): Flow<Pair<List<Person>?, String?>> {
        return repository.searchNames(expression).map { result ->
            when(result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }
                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}