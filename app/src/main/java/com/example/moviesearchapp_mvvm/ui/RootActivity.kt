package com.example.moviesearchapp_mvvm.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.moviesearchapp_mvvm.R
import com.example.moviesearchapp_mvvm.databinding.ActivityRootBinding
import com.example.moviesearchapp_mvvm.ui.movies.MoviesFragment

class RootActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRootBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_root)

        binding = ActivityRootBinding.inflate(layoutInflater)
        binding.root
        if (savedInstanceState == null) {
            // Добавляем фрагмент в контейнер
            supportFragmentManager.commit {
                this.add(R.id.rootFragmentContainerView, MoviesFragment())
            }
        }

    }
}