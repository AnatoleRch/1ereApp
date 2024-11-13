package com.example.a1ereapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel() : ViewModel() {
    val apikey = "317519a83cc36ab9367ba50e5aa75b40"
    val movies = MutableStateFlow<List<Film>>(listOf())
    val actors = MutableStateFlow<List<Acteur>>(listOf())
    val tvShow = MutableStateFlow<List<Serie>>(listOf())
    val movieDetails = MutableStateFlow<DetailsDuFilm>(DetailsDuFilm())
    val tvShowDetails = MutableStateFlow<DetailsDeLaSerie>(DetailsDeLaSerie())
    val actorDetails = MutableStateFlow<DetailsDeLActeur>(DetailsDeLActeur())
    val collection = MutableStateFlow<List<Collection>>(listOf())

    val service = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(TmdbAPI::class.java)

    fun get_films_tendance(){
        viewModelScope.launch {
            movies.value = service.films_tendance(apikey).results
        }
    }

    fun get_series_tendance(){
        viewModelScope.launch {
            tvShow.value = service.series_tendance(apikey).results
        }
    }

    fun get_acteurs_tendance(){
        viewModelScope.launch {
            actors.value = service.acteurs_tendance(apikey).results
        }
    }

    fun get_details_film(id: Int){
        viewModelScope.launch {
            movieDetails.value = service.details_film(id, apikey)
        }
    }

    fun get_details_serie(id: Int){
        viewModelScope.launch {
            tvShowDetails.value = service.details_serie(id, apikey)
        }
    }

    fun get_details_acteur(id: Int){
        viewModelScope.launch {
            actorDetails.value = service.details_acteur(id, apikey)
        }
    }

    fun rechercherFilms(query: String) {
        viewModelScope.launch {
            movies.value = service.rechercherFilms(apikey, query).results
        }
    }

    fun rechercherSeries(query: String) {
        viewModelScope.launch {
            try {
                tvShow.value = service.rechercherSeries(apikey, query).results
            } catch (e: Exception) {
                Log.e("MainViewModel", "Erreur lors de la recherche des acteurs", e)
            }
        }
    }

    fun rechercherActeurs(query: String) {
        viewModelScope.launch {
            try {
                actors.value = service.rechercherActeurs(apikey, query).results
            } catch (e: Exception) {
                Log.e("MainViewModel", "Erreur lors de la recherche des acteurs", e)
            }
        }
    }

    fun get_collection(query: String) {
        viewModelScope.launch {
            try {
                collection.value = service.collection(apikey, query).results
            } catch (e: Exception) {
                Log.e("MainViewModel", "Erreur lors de la recherche des acteurs", e)
            }
        }
    }

    fun get_collection_horror(query: String = "horror") {
        viewModelScope.launch {
            try {
                collection.value = service.collection(apikey, query).results
            } catch (e: Exception) {
                Log.e("MainViewModel", "Erreur lors de la recherche des acteurs", e)
            }
        }
    }
}