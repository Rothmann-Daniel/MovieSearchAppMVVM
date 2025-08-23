package com.example.moviesearchapp_mvvm.ui.poster

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.moviesearchapp_mvvm.R
import com.example.moviesearchapp_mvvm.presentation.posters.PosterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PosterActivity : AppCompatActivity() {
    private val viewModel: PosterViewModel by viewModel(parameters = {
        parametersOf(intent.extras?.getString("poster", "") ?: "")
    })


    private lateinit var poster: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)
        poster = findViewById(R.id.poster)
        val imageUrl = intent.extras?.getString("poster", "") ?: ""
        viewModel.observeUrl()?.observe(this) {
            setupPosterImage(it)
        }

    }
    fun setupPosterImage(url: String) {
        Glide.with(applicationContext)
            .load(url)
            .into(poster)
    }
}