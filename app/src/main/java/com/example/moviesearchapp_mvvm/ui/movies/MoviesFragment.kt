package com.example.moviesearchapp_mvvm.ui.movies

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearchapp_mvvm.R
import com.example.moviesearchapp_mvvm.databinding.FragmentMoviesBinding
import com.example.moviesearchapp_mvvm.domain.models.Movie
import com.example.moviesearchapp_mvvm.presentation.movies.MoviesState
import com.example.moviesearchapp_mvvm.presentation.movies.MoviesViewModel
import com.example.moviesearchapp_mvvm.ui.poster.DetailsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private val viewModel by viewModel<MoviesViewModel>()

    private val handler = Handler(Looper.getMainLooper())

    private lateinit var binding: FragmentMoviesBinding

    private lateinit var queryInput: EditText
    private lateinit var placeholderMessage: TextView
    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var textWatcher: TextWatcher

    private var isClickAllowed = true

    private val adapter = MoviesAdapter { movie ->
        if (clickDebounce()) {

            // Навигируемся на следующий экран
            parentFragmentManager.commit {
                replace(
                    // Указали, в каком контейнере работаем
                    R.id.rootFragmentContainerView,
                    // Создали фрагмент
                    DetailsFragment.newInstance(
                        movieId = movie.id,
                        posterUrl = movie.image
                    ),
                    // Указали тег фрагмента
                    DetailsFragment.TAG
                )

                // Добавляем фрагмент в Back Stack
                addToBackStack(DetailsFragment.TAG)
            }

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        placeholderMessage = binding.placeholderMessage
        queryInput = binding.queryInput
        moviesRecyclerView = binding.locations
        progressBar = binding.progressBar

        moviesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        moviesRecyclerView.adapter = adapter

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel?.observeShowToast()?.observe(viewLifecycleOwner) {
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
            handler.postDelayed({ isClickAllowed = true },
                com.example.moviesearchapp_mvvm.ui.movies.MoviesFragment.CLICK_DEBOUNCE_DELAY
            )
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
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
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