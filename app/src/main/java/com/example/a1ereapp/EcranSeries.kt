package com.example.a1ereapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun EcranSeries(
    navController: NavController,
    viewModel: MainViewModel,
){

    val series by viewModel.tvShow.collectAsState()

    LaunchedEffect(true) {
        viewModel.get_series_tendance()
    }

    // Affichage des films en 2 colonnes avec affichage des posters et titres
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Utilise 2 colonnes
        modifier = Modifier.padding(8.dp),

        ) {
        items(series) { serie ->
            SerieItem(serie = serie, onClick = {
                navController.navigate("DetailsSerie/${serie.id}")
            })
        }
    }
}

@Composable
fun SerieItem(serie: Serie, onClick: () -> Unit) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            //containerColor = Color(0xFFb3ff7a) ,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${serie.poster_path}",
                contentDescription = "https://image.tmdb.org/t/p/w500${serie.poster_path}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
            )
            Text(
                text = serie.name,
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Center ,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
