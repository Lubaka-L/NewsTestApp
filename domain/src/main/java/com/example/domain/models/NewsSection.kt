package com.example.domain.models

import com.example.domain.enums.Section

data class NewsSection(
    val section: Section,
    val news: List<News>
)
