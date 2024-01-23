package com.example.data.networking.general

import com.google.gson.annotations.SerializedName

data class ListWrapper<T>(
    @SerializedName("articles")
    val articles: T
)