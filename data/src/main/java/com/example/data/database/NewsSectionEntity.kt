package com.example.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sections_table")
data class NewsSectionEntity(
    @PrimaryKey
    val section: String
)