package com.example.moviesearchapp_mvvm.domain.api

import com.example.moviesearchapp_mvvm.domain.models.Person
import com.example.moviesearchapp_mvvm.util.Resource
import kotlinx.coroutines.flow.Flow

interface NamesRepository {
    fun searchNames(expression: String): Flow<Resource<List<Person>>>
}