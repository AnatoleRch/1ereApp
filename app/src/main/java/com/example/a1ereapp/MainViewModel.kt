package com.example.a1ereapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel() : ViewModel() {
    val apikey = "317519a83cc36ab9367ba50e5aa75b40"
    val movies = MutableStateFlow<List<Film>>(listOf())
    val service = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(TmdbAPI::class.java)

    fun get_film_tendance(){
        viewModelScope.launch {
            movies.value = service.films_tendance(apikey).results
        }
    }
}