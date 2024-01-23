package com.example.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "news",
    foreignKeys = [ForeignKey(
        entity = NewsSectionEntity::class,
        parentColumns = ["section"],
        childColumns = ["sectionName"]
    )]
)
data class NewsEntity(
    @PrimaryKey
    val url: String,
    val sectionName: String,
    val image: String,
    val header: String,
    val description: String,
    val source: String,
    val publishedAt: String
)