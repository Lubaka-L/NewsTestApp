package com.example.data.networking.api

import com.example.data.networking.general.ListWrapper
import com.example.domain.models.News
import org.koin.android.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Целенаправленно не прятала ключ
const val API_KEY = "d5bde7352e0a24c66f5d55986157001e"

interface NewsApi {

    @GET("/api/v4/top-headlines")
    suspend fun getNews(
        @Query("apikey") apikey: String = API_KEY,
        @Query("country") country: String = "ru",
        @Query("category") category: String
    ): Response<ListWrapper<List<News>>>

}