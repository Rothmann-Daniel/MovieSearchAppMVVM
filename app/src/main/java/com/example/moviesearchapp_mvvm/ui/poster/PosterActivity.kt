package com.example.moviesearchapp_mvvm.ui.poster

import android.app.Activity
import android.os.Bundle
import com.example.moviesearchapp_mvvm.Creator
import com.example.moviesearchapp_mvvm.R

class PosterActivity : Activity() {

    private val posterController = Creator.providePosterController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)
        posterController.onCreate()
    }
}