package com.example.moviesearchapp_mvvm.presentation.posters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviesearchapp_mvvm.ui.PosterFragment
import com.example.moviesearchapp_mvvm.ui.poster.AboutFragment

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
            0 -> PosterFragment.newInstance(posterUrl)
            else -> AboutFragment.newInstance(movieId)
        }
    }
}