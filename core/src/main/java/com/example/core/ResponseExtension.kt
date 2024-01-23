package com.example.core

import retrofit2.Response

object ResponseExtension {

    /** Обрабатывает [retrofit2.Response] и возвращает ResultWrapper */
    fun <T : Any> Response<T>.handleResponse(): ResultWrapper<T> {
        return try {
            if (isSuccessful) {
                return this.body()?.let { body ->
                    ResultWrapper.Success(body)
                } ?: ResultWrapper.Error(
                    error = "Empty Body received"
                )
            } else {
                ResultWrapper.Error("Something went wrong...")
            }
        } catch (exception: Exception) {
            ResultWrapper.Error(
                error = "$exception\n"
            )
        }
    }
}