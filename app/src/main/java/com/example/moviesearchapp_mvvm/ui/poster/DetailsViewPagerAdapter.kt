package com.example.moviesearchapp_mvvm.ui.poster

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val posterUrl: String,
    private val movieId: String,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> createPosterFragment(posterUrl)
            else -> createAboutFragment(movieId)
        }
    }

    private fun createPosterFragment(posterUrl: String): Fragment {
        return PosterFragment().apply {
            arguments = Bundle().apply {
                putString("poster_url", posterUrl)
            }
        }
    }

    private fun createAboutFragment(movieId: String): Fragment {
        return AboutFragment().apply {
            arguments = Bundle().apply {
                putString("movie_id", movieId)
            }
        }
    }
}