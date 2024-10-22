package com.example.a1ereapp

import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbAPI {
    @GET("trending/movie/week")
    suspend fun films_tendance(@Query("api_key") api_key:String): Films

    @GET("trending/movie/week")
    suspend fun series_tendance(@Query("api_key") api_key:String): Films

    @GET("trending/movie/week")
    suspend fun acteurs_tendance(@Query("api_key") api_key:String): Films
}