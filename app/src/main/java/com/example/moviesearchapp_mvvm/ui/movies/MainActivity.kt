package com.example.moviesearchapp_mvvm.ui.movies

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearchapp_mvvm.R
import com.example.moviesearchapp_mvvm.domain.models.Movie
import com.example.moviesearchapp_mvvm.presentation.movies.MoviesState
import com.example.moviesearchapp_mvvm.presentation.movies.MoviesViewModel
import com.example.moviesearchapp_mvvm.ui.poster.PosterActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {
    private val viewModel: MoviesViewModel by viewModel(parameters = { parametersOf(applicationContext) })

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private lateinit var queryInput: EditText
    private lateinit var placeholderMessage: TextView
    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: MoviesAdapter

    private var textWatcher: TextWatcher? = null
    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        placeholderMessage = findViewById(R.id.placeholderMessage)
        queryInput = findViewById(R.id.queryInput)
        moviesRecyclerView = findViewById(R.id.movies) // Исправлено на правильный ID
        progressBar = findViewById(R.id.progressBar)

        adapter = MoviesAdapter { movie ->
            if (clickDebounce()) {
                val intent = Intent(this, PosterActivity::class.java)
                intent.putExtra("poster", movie.image)
                startActivity(intent)
            }
        }

        moviesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        moviesRecyclerView.adapter = adapter

        viewModel.observeState().observe(this) {
            render(it)
        }

        viewModel?.observeShowToast()?.observe(this) {
            showToast(it.toString())
        }

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(s: Editable?) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel?.searchDebounce(s?.toString() ?: "")
            }
        }
        textWatcher?.let { queryInput.addTextChangedListener(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        textWatcher?.let { queryInput.removeTextChangedListener(it) }
    }

    private fun clickDebounce() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    fun showLoading() {
        moviesRecyclerView.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    fun showContent(moviesList: List<Movie>) {
        moviesRecyclerView.visibility = View.VISIBLE
        placeholderMessage.visibility = View.GONE
        progressBar.visibility = View.GONE
        adapter.updateMovies(moviesList)
    }

    fun showError(errorMessage: String) {
        moviesRecyclerView.visibility = View.GONE
        placeholderMessage.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        placeholderMessage.text = errorMessage
    }

    fun showEmpty(emptyMessage: String) {
        showError(emptyMessage)
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun render(state: MoviesState) {
        when (state) {
            is MoviesState.Loading -> showLoading()
            is MoviesState.Content -> showContent(state.movies)
            is MoviesState.Error -> showError(state.errorMessage)
            is MoviesState.Empty -> showEmpty(state.message)
        }
    }
}