package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NewsEntity::class, NewsSectionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    internal abstract fun newsDao(): NewsDao
}