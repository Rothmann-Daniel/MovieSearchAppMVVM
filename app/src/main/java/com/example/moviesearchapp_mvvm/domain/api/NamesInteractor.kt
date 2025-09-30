package com.example.moviesearchapp_mvvm.domain.api

import com.example.moviesearchapp_mvvm.domain.models.Person
import kotlinx.coroutines.flow.Flow

interface NamesInteractor {
    fun searchNames(expression: String): Flow<Pair<List<Person>?, String?>>
}