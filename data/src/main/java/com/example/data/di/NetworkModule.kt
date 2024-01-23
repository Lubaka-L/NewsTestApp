package com.example.data.di

import com.example.data.networking.api.NewsApi
import com.example.data.networking.serialization.adapters.NewsBodyAdapter
import com.example.domain.models.News
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL_CURRENCY = "https://gnews.io/"

val networkModule: Module = module {
    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single<Gson> {
        GsonBuilder()
            .registerTypeAdapter(
                News::class.java,
                NewsBodyAdapter()
            ).setLenient()
            .create()
    }

    single<Retrofit.Builder> {
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
    }

    single<NewsApi> {
        get<Retrofit.Builder>().baseUrl(BASE_URL_CURRENCY).build().create(NewsApi::class.java)
    }
}