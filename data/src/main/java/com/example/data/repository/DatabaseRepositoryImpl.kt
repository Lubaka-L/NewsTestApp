package com.example.data.repository

import com.example.data.database.NewsDatabase
import com.example.data.database.mappers.toNewsEntity
import com.example.data.database.mappers.toNewsSection
import com.example.data.database.mappers.toNewsSectionEntity
import com.example.domain.models.NewsSection
import com.example.domain.repository.DatabaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DatabaseRepositoryImpl(
    private val database: NewsDatabase,
    private val dispatcher: CoroutineDispatcher
) : DatabaseRepository {
    override suspend fun addNews(news: List<NewsSection>) {
        withContext(dispatcher) {
            database.newsDao().addNewsSections(news.map { it.toNewsSectionEntity() })
            news.forEach { newsSection ->
                database.newsDao()
                    .addNews(newsSection.news.map { it.toNewsEntity(newsSection.section.russialName) })
            }
        }
    }

    override suspend fun getNews(): List<NewsSection> {
        return withContext(dispatcher) {
            val result = mutableListOf<NewsSection>()
            val sections = database.newsDao().getNewsSections()
            sections.forEach { sectionEntity ->
                val news = database.newsDao().getNews(sectionEntity.section)
                result.add(sectionEntity.toNewsSection(news))
            }
            result
        }
    }
}