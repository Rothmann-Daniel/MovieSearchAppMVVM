package com.example.moviesearchapp_mvvm.ui.cast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesearchapp_mvvm.databinding.ActivityMoviesCastBinding
import com.example.moviesearchapp_mvvm.presentation.cast.MoviesCastState
import com.example.moviesearchapp_mvvm.presentation.cast.MoviesCastViewModel
import com.example.moviesearchapp_mvvm.ui.cast.MoviesCastAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MoviesCastActivity : AppCompatActivity() {

    companion object {

        private const val ARGS_MOVIE_ID = "movie_id"

        fun newInstance(context: Context, movieId: String): Intent {
            return Intent(context, MoviesCastActivity::class.java).apply {
                putExtra(ARGS_MOVIE_ID, movieId)
            }
        }

    }

    private val moviesCastViewModel: MoviesCastViewModel by viewModel {
        parametersOf(intent.getStringExtra(ARGS_MOVIE_ID))
    }

    private val adapter = MoviesCastAdapter()

    private lateinit var binding: ActivityMoviesCastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoviesCastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.moviesCastRecyclerView.adapter = adapter
        binding.moviesCastRecyclerView.layoutManager = LinearLayoutManager(this)

        // Наблюдаем за UiState из ViewModel
        moviesCastViewModel.observeState().observe(this) {
            // В зависимости от UiState экрана показываем разные состояния экрана
            when (it) {
                is MoviesCastState.Content -> showContent(it)
                is MoviesCastState.Error -> showError(it)
                is MoviesCastState.Loading -> showLoading()
            }
        }
    }

    private fun showLoading() {
        binding.contentContainer.isVisible = false
        binding.errorMessageTextView.isVisible = false

        binding.progressBar.isVisible = true
    }

    private fun showError(state: MoviesCastState.Error) {
        binding.contentContainer.isVisible = false
        binding.progressBar.isVisible = false

        binding.errorMessageTextView.isVisible = true
        binding.errorMessageTextView.text = state.message
    }

    private fun showContent(state: MoviesCastState.Content) {
        binding.progressBar.isVisible = false
        binding.errorMessageTextView.isVisible = false

        binding.contentContainer.isVisible = true
        binding.movieTitle.text = state.movie.fullTitle

        // Просто объединяем всех участников в единый список и отображаем
        adapter.persons = state.movie.directors + state.movie.writers + state.movie.actors + state.movie.others
        adapter.notifyDataSetChanged()
    }
}