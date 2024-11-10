package com.example.a1ereapp

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbAPI {
    // Recherche de films par titre
    @GET("search/movie")
    suspend fun rechercherFilms(
        @Query("api_key") api_key: String,
        @Query("query") query: String
    ): Films

    // Recherche de séries par titre
    @GET("search/tv")
    suspend fun rechercherSeries(
        @Query("api_key") api_key: String,
        @Query("query") query: String
    ): Series

    // Recherche d'acteurs par nom
    @GET("search/person")
    suspend fun rechercherActeurs(
        @Query("api_key") api_key: String,
        @Query("query") query: String
    ): Acteurs

    @GET("trending/movie/week")
    suspend fun films_tendance(
        @Query("api_key") api_key:String,
        @Query("language") language: String ="fr"
    ): Films

    @GET("trending/tv/week")
    suspend fun series_tendance(
        @Query("api_key") api_key:String,
        @Query("language") language: String ="fr"
    ): Series

    @GET("trending/person/week")
    suspend fun acteurs_tendance(
        @Query("api_key") api_key:String,
        @Query("language") language: String ="fr"
    ): Acteurs

    @GET("movie/{id}")
    suspend fun details_film(
        @Path("id") id: Int,               // L'ID de l'acteur sera passé ici
        @Query("api_key") api_key: String,   // Clé API pour authentification
        @Query("append_to_response") appendToResponse: String = "credits",
        @Query("language") language: String ="fr"
    ): DetailsDuFilm

    @GET("tv/{id}")
    suspend fun details_serie(
        @Path("id") id: Int,               // L'ID de l'acteur sera passé ici
        @Query("api_key") api_key: String,   // Clé API pour authentification
        @Query("append_to_response") appendToResponse: String = "credits",
        @Query("language") language: String ="fr"
    ): DetailsDeLaSerie

    @GET("person/{id}")
    suspend fun details_acteur(
        @Path("id") id: Int,               // L'ID de l'acteur sera passé ici
        @Query("api_key") api_key: String,   // Clé API pour authentification
        @Query("append_to_response") appendToResponse: String = "credits",
        @Query("language") language: String ="fr"
    ): DetailsDeLActeur
}