package com.example.domain.repository

import com.example.core.ResultWrapper
import com.example.domain.enums.Section
import com.example.domain.models.NewsSection
import kotlinx.coroutines.flow.StateFlow

interface Repository {

    suspend fun getNews(categories: List<Section>): List<ResultWrapper<NewsSection>>

}