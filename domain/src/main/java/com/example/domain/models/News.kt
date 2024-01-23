package com.example.domain.models

import android.net.Uri

data class News(
    val image: Uri,
    val header: String,
    val description: String,
    val source: String,
    val publishedAt: String,
    val url: Uri
)