package com.example.core

sealed class ResultWrapper<T> {
    data class Success<T>(val data: T) : ResultWrapper<T>()
    data class Error<T>(val error: String? = null) : ResultWrapper<T>()
}


sealed class ResultWrapperUI<out T> {
    data class Success<T>(val data: T) : ResultWrapperUI<T>()
    data class Error<T>(val error: String) : ResultWrapperUI<T>()
    data object Loading : ResultWrapperUI<Nothing>()
}