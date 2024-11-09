package com.example.a1ereapp

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import coil.compose.AsyncImage

@Composable
fun EcranActeurs(
    navController: NavController,
    viewModel: MainViewModel,
    windowSizeClass: WindowSizeClass
){

    val acteurs by viewModel.actors.collectAsState()

    LaunchedEffect(true) {
        viewModel.get_acteurs_tendance()
    }

    when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // Utilise 2 colonnes
                modifier = Modifier.padding(8.dp),

                ) {
                items(acteurs) { acteur ->
                    ActeurItem(acteur = acteur, onClick = {
                        navController.navigate("DetailsActeur/${acteur.id}")
                        Log.d("id","DetailsActeur/${acteur.id}")
                    })
                }
            }
        }
        else -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4), // Utilise 2 colonnes
                modifier = Modifier.padding(8.dp),

                ) {
                items(acteurs) { acteur ->
                    ActeurItem(acteur = acteur, onClick = {
                        navController.navigate("DetailsActeur/${acteur.id}")
                        Log.d("id","DetailsActeur/${acteur.id}")
                    })
                }
            }
        }
    }
}

@Composable
fun ActeurItem(acteur: Acteur, onClick: () -> Unit) {

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
            if (acteur.profile_path == null) {
                Icon(
                    painter = painterResource(id = R.drawable.person),
                    contentDescription = "user Icon",
                    modifier = Modifier.size(256.dp)
                )
            } else {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${acteur.profile_path}",
                    contentDescription = "https://image.tmdb.org/t/p/w500${acteur.profile_path}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                )
            }
            Text(
                text = acteur.name,
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}