package com.example.moviesearchapp_mvvm.domain.api

import com.example.moviesearchapp_mvvm.domain.models.Person
import com.example.moviesearchapp_mvvm.util.Resource

interface NamesRepository {

    fun searchNames(expression: String): Resource<List<Person>>
}