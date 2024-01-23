package com.example.data.database.mappers

import android.net.Uri
import com.example.data.database.NewsEntity
import com.example.data.database.NewsSectionEntity
import com.example.domain.enums.Section
import com.example.domain.models.News
import com.example.domain.models.NewsSection

internal fun News.toNewsEntity(sectionName: String): NewsEntity {
    return NewsEntity(
        url = url.toString(),
        image = image.toString(),
        header = header,
        description = description,
        source = source,
        publishedAt = publishedAt,
        sectionName = sectionName
    )
}

internal fun NewsSection.toNewsSectionEntity(): NewsSectionEntity {
    return NewsSectionEntity(
        section = section.russialName
    )
}

internal fun NewsEntity.toNews(): News {
    return News(
        url = Uri.parse(url),
        image = Uri.parse(image),
        header = header,
        description = description,
        source = source,
        publishedAt = publishedAt
    )
}

internal fun NewsSectionEntity.toNewsSection(news: List<NewsEntity>): NewsSection {
    return NewsSection(
        section = Section.entries.firstOrNull { it.russialName == section } ?: Section.General,
        news = news.map { it.toNews() }
    )
}