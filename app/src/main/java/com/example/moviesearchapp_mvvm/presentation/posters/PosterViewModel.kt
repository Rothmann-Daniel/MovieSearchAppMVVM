package com.example.moviesearchapp_mvvm.presentation.posters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class PosterViewModel(posterUrl:String): ViewModel() {
    private val urlLiveData= MutableLiveData(posterUrl)
    fun observeUrl(): LiveData<String> = urlLiveData
    companion object{
        fun getFactory(url: String): ViewModelProvider.Factory= viewModelFactory {
            initializer {
                PosterViewModel(url)
            }
        }
    }
}