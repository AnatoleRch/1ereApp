package com.example.a1ereapp

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowSizeClass

@Composable
fun DetailsActeur(
    navController: NavController,
    viewModel: MainViewModel,
    id: Int,
    windowClass: WindowSizeClass
) {LaunchedEffect(id) {
    viewModel.get_details_acteur(id)
}

    val actorDetails by viewModel.actorDetails.collectAsState(initial = null)

    actorDetails.let { actor ->
        Column(modifier = Modifier.padding(16.dp)) {
            if (actor != null) {
                Text(text = "Nom: ${actor.name}", fontSize = 20.sp)
            }


        }
    }
}