package com.example.moviesearchapp_mvvm.util

import android.app.Application
import com.example.moviesearchapp_mvvm.di.interactorModule
import com.example.moviesearchapp_mvvm.di.networkModule
import com.example.moviesearchapp_mvvm.di.repositoryModule
import com.example.moviesearchapp_mvvm.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class MoviesApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidContext(this@MoviesApplication)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    interactorModule,
                    viewModelModule
                )
            )
        }
    }
}