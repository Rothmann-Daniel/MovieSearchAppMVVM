package com.example.moviesearchapp_mvvm.ui.cast

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearchapp_mvvm.R
import com.example.moviesearchapp_mvvm.domain.models.MovieCastPerson

class MovieCastViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_cast, parent, false)
    ) {

    var actorImage: ImageView = itemView.findViewById(R.id.actorImageView)
    var personName: TextView = itemView.findViewById(R.id.actorNameTextView)
    var personDescription: TextView = itemView.findViewById(R.id.actorDescriptionTextView)

    fun bind(movieCastPerson: MovieCastPerson) {
        if (movieCastPerson.image == null) {
            actorImage.isVisible = false
        } else {
            Glide.with(itemView)
                .load(movieCastPerson.image)
                .into(actorImage)
            actorImage.isVisible = true
        }

        personName.text = movieCastPerson.name
        personDescription.text = movieCastPerson.description
    }
}