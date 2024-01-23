package com.example.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewsSections(news: List<NewsSectionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNews(news: List<NewsEntity>)

    @Query("SELECT * FROM sections_table ORDER BY section ASC")
    suspend fun getNewsSections(): List<NewsSectionEntity>

    @Query("SELECT * FROM news WHERE sectionName IS :section")
    suspend fun getNews(section: String): List<NewsEntity>

}