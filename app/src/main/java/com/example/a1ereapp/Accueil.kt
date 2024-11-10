package com.example.a1ereapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

@Composable
fun Accueil( navController: NavController, windowSizeClass: WindowSizeClass) {
    when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Photo()
                Nom()
                Pres()
                Spacer(Modifier.height(20.dp))
                Coordonnees()
                Spacer(Modifier.height(20.dp))
                Demarrer(navController)

            }
        }
        else -> {
            Row(
                Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.5f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Photo()
                    Nom()
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Pres()
                    Spacer(Modifier.height(50.dp))
                    Coordonnees()
                    Spacer(Modifier.height(20.dp))
                    Demarrer(navController)
                }
            }
        }
    }
}

@Composable
fun Photo() {
    Image(
        painterResource(id = R.drawable.bonbon),
        contentDescription = "Des bonbons !",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(250.dp)
            .clip(CircleShape)
    )
}
@Composable
fun Nom() {
    Text(
        text = "Anatole Roché",
        style = MaterialTheme.typography.displayMedium,
        modifier = Modifier.padding(20.dp)
    )
}

@Composable
fun Pres() {
    Text(
        text = "Futur ingénieur informaticien" ,
    )
}

@Composable
fun Demarrer(navController: NavController) {
    Button(
        onClick = { navController.navigate(EcranFilms()) },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
    ) {
        Text(text = "Démarrer", color = Color.White)
    }
}

@Composable
fun Coordonnees() {
    Row()
    {
        Image(
            painterResource(id = R.drawable.linkedin),
            contentDescription = "Icon Linkedin",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(23.dp)
        )
        Text(
            text = " www.linkedin.com"
        )

    }

    Row(
    ) {
        Image(
            painterResource(id = R.drawable.gmail),
            contentDescription = "Icon mail",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(23.dp)
        )
        Text(
            text = " anatoleroche.blq@gmail.com"
        )
    }
}


