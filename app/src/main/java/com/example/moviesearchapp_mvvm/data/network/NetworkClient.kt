package com.example.moviesearchapp_mvvm.data.network

import com.example.moviesearchapp_mvvm.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response

}