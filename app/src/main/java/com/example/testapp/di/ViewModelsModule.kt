package com.example.testapp.di

import com.example.testapp.ui.screens.news_screen.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { NewsViewModel(get()) }
}