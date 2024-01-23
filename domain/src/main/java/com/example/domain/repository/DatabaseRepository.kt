package com.example.domain.repository

import com.example.domain.models.NewsSection

interface DatabaseRepository {

    suspend fun addNews(news: List<NewsSection>)
    suspend fun getNews(): List<NewsSection>

}