package com.example.retrofit_get_request_example

import retrofit2.http.GET

interface ApiService {
    @GET("d4e2bt6jba6cmiekqmsv")
    suspend fun getIp(): MyIp
}