package com.example.data.di

import com.example.data.repository.RepositoryImpl
import com.example.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {
    single<Repository> { RepositoryImpl(get(), get()) }
}