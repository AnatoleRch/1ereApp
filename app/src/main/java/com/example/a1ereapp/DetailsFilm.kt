package com.example.a1ereapp

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.window.core.layout.WindowSizeClass

@Composable
fun DetailsFilm(
    navController: NavController,
    viewModel: MainViewModel,
    id: Int,
    windowClass: WindowSizeClass
) {
    LaunchedEffect(id) {
    viewModel.get_details_film(id)
}

    val filmDetails by viewModel.movieDetails.collectAsState()

    filmDetails.let { film ->
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nom: ${film.original_title}", fontSize = 20.sp)


        }
    }
}