package com.example.data.di

import androidx.room.Room
import com.example.data.database.NewsDatabase
import com.example.data.repository.DatabaseRepositoryImpl
import com.example.domain.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule = module {
    provideAppDatabase()
    provideDatabase()
}

fun Module.provideAppDatabase() {
    this.single<NewsDatabase> {
        Room.databaseBuilder(get(), NewsDatabase::class.java, "database")
            .fallbackToDestructiveMigration().build()
    }
}

fun Module.provideDatabase() {
    single<DatabaseRepository> {
        DatabaseRepositoryImpl(get(), Dispatchers.IO)
    }
}