package com.example.moviesearchapp_mvvm.ui.cast

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearchapp_mvvm.domain.models.MovieCastPerson
import com.example.moviesearchapp_mvvm.ui.cast.MovieCastViewHolder

class MoviesCastAdapter : RecyclerView.Adapter<MovieCastViewHolder>() {

    var persons = emptyList<MovieCastPerson>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCastViewHolder =
        MovieCastViewHolder(parent)

    override fun onBindViewHolder(holder: MovieCastViewHolder, position: Int) {
        holder.bind(persons.get(position))
    }

    override fun getItemCount(): Int = persons.size

}