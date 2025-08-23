package com.example.moviesearchapp_mvvm.ui.cast

import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.moviesearchapp_mvvm.core.ui.RVItem
import com.example.moviesearchapp_mvvm.databinding.ListItemCastBinding
import com.example.moviesearchapp_mvvm.databinding.ListItemHeaderBinding
import com.example.moviesearchapp_mvvm.presentation.cast.MoviesCastRVItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

// Делегат для заголовков на экране состава участников
fun movieCastHeaderDelegate() = adapterDelegateViewBinding<MoviesCastRVItem.HeaderItem, RVItem, ListItemHeaderBinding>(
    { layoutInflater, root -> ListItemHeaderBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        binding.headerTextView.text = item.headerText
    }
}

// Делегат для отображения участников на соответствующем экране
fun movieCastPersonDelegate() = adapterDelegateViewBinding<MoviesCastRVItem.PersonItem, RVItem, ListItemCastBinding>(
    { layoutInflater, root -> ListItemCastBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        if (item.data.image == null) {
            binding.actorImageView.isVisible = false
        } else {
            Glide.with(itemView)
                .load(item.data.image)
                .into(binding.actorImageView)
            binding.actorImageView.isVisible = true
        }

        binding.actorNameTextView.text = item.data.name
        binding.actorDescriptionTextView.text = item.data.description
    }
}