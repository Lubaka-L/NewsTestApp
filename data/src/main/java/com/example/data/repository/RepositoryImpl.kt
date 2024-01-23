package com.example.data.repository

import com.example.core.ResponseExtension.handleResponse
import com.example.core.ResultWrapper
import com.example.data.networking.api.NewsApi
import com.example.data.networking.general.ListWrapper
import com.example.domain.enums.Section
import com.example.domain.models.News
import com.example.domain.models.NewsSection
import com.example.domain.repository.DatabaseRepository
import com.example.domain.repository.Repository
import kotlinx.coroutines.delay

class RepositoryImpl(
    private val newsApi: NewsApi,
    private val databaseRepository: DatabaseRepository
) : Repository {

    override suspend fun getNews(categories: List<Section>): List<ResultWrapper<NewsSection>> {
        return try {
            val result = categories.map { section ->
                val newsSectionResult = requestNewsSection(section)
                newsSectionResult.parseResultTo(section)
            }
            val successedList: List<NewsSection> = result.mapNotNull {
                when (it) {
                    is ResultWrapper.Success -> it.data
                    is ResultWrapper.Error -> null
                }
            }
            databaseRepository.addNews(successedList)
            if (successedList.isEmpty()) {
                databaseRepository.getNews().map {
                    ResultWrapper.Success(it)
                }
            } else {
                result
            }
        } catch (e: Exception) {
            databaseRepository.getNews().map {
                ResultWrapper.Success(it)
            }
        }
    }

    private suspend fun requestNewsSection(section: Section): ResultWrapper<ListWrapper<List<News>>> {
        // Cтавлю delay потому что api блокирует множественные запросы, по этой же причине не используется
        // параллельная загрузка новостей чз async{}
        delay(800)
        return newsApi.getNews(category = section.name.lowercase()).handleResponse()
    }

    private fun ResultWrapper<ListWrapper<List<News>>>.parseResultTo(section: Section): ResultWrapper<NewsSection> {
        return when (this) {
            is ResultWrapper.Success -> ResultWrapper.Success(
                data = NewsSection(section, data.articles)
            )

            is ResultWrapper.Error -> ResultWrapper.Error()
        }
    }
}