package com.example.testapp.ui.screens.news_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.ResultWrapper
import com.example.core.ResultWrapperUI
import com.example.domain.enums.Section
import com.example.domain.models.NewsSection
import com.example.domain.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: Repository) : ViewModel() {

    private var originalList: List<NewsSection> = listOf()
    private val _news: MutableStateFlow<ResultWrapperUI<List<NewsSection>>> =
        MutableStateFlow(ResultWrapperUI.Loading)
    val news = _news.asStateFlow()

    init {
        loadNews()
    }

    private fun loadNews() {
        viewModelScope.launch {
            val categoriesToShow = listOf(
                Section.Business,
                Section.General,
                Section.Health,
                Section.Science,
                Section.Nation,
                Section.Entertainment,
                Section.Sports,
                Section.Technology
            )
            val result: List<ResultWrapper<NewsSection>> = repository.getNews(categoriesToShow)

            val successedList = mutableListOf<NewsSection>()
            result.forEach {
                when (it) {
                    is ResultWrapper.Success -> {
                        successedList.add(it.data)
                    }

                    is ResultWrapper.Error -> {}
                }
            }
            originalList = successedList
            _news.update {
                ResultWrapperUI.Success(successedList)
            }
        }
    }

    fun searchNewsByTitleOrDescription(query: String) {
        val resultList = originalList.map { newSection ->
            newSection.copy(
                news = newSection.news.filter { news ->
                    news.header.lowercase().contains(query) || news.description.lowercase()
                        .contains(query)
                }
            )
        }.filter { it.news.isNotEmpty() }

        _news.update {
            ResultWrapperUI.Success(resultList)
        }
    }
}


