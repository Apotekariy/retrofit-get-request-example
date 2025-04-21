package com.example.retrofit_get_request_example

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val json = Json{ignoreUnknownKeys = true}

object NetworkService {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://functions.yandexcloud.net/")
        .addConverterFactory(
            json.asConverterFactory(
                "application/json".toMediaType())
        )
        .build()

    val api: ApiService = retrofit.create(ApiService::class.java)

}

