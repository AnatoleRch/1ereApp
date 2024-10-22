package com.example.a1ereapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels()
        setContent {
            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            val navController = rememberNavController()

            val currentBackStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute = currentBackStackEntry.value?.destination?.route

            Scaffold(
                bottomBar = {
                    if (currentRoute != "Acceuil") {  // Ne pas afficher la BottomNavBar sur la page Acceuil
                        BottomNavBar(navController)
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "Acceuil",
                    Modifier.padding(innerPadding) // Assurer que le contenu ne soit pas masqué par la Bottom Navigation
                ) {
                    composable("Acceuil") { Acceuil(navController, windowSizeClass) }
                    composable("EcranFilms") { EcranFilms(navController, viewModel()) }
                    composable("EcranSeries") { EcranSeries(navController, viewModel()) }
                    composable("EcranActeurs") { EcranActeurs(navController, viewModel()) }
                    composable("DetailsFilm") { }
                    composable("DetailsSerie") { }
                    composable("DetailsActeur") { }
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavController) {
    val destinations = listOf("Acceuil", "EcranFilms", "EcranSeries", "EcranActeurs")
    val labels = listOf("Accueil", "Films", "Séries", "Acteurs")
    val icons = listOf(R.drawable.home, R.drawable.movie, R.drawable.tv, R.drawable.person)
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route
    NavigationBar(containerColor = Color(0xFF0c761d) , contentColor = Color.Black) {
        destinations.forEachIndexed { index, destination ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = icons[index]),
                        contentDescription = destination,
                        modifier = Modifier.size(35.dp)
                    )
                },
                label = { Text(labels[index]) },
                selected = currentRoute == destination,
                onClick = {
                    navController.navigate(destination)
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Black ,
                    unselectedTextColor = Color.Black,
                    selectedTextColor = Color.Black,
                    indicatorColor =  Color(0xFFb3ff7a)
                )
            )
        }
    }
}