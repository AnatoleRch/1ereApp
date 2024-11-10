package com.example.a1ereapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.serialization.Serializable
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.window.core.layout.WindowWidthSizeClass


@Serializable
class Acceuil
@Serializable
class EcranFilms
@Serializable
class EcranSeries
@Serializable
class EcranActeurs


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels()
        setContent {
            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            var query by remember { mutableStateOf("") }
            var active by remember { mutableStateOf(false) }
            Scaffold(
                topBar = {
                    if (currentDestination?.hasRoute<EcranFilms>() == true
                        || currentDestination?.hasRoute<EcranSeries>() == true
                        || currentDestination?.hasRoute<EcranActeurs>() == true) {
                        SearchBar(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "icone search"
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    modifier = Modifier.clickable {
                                        if (query.isNotEmpty()) {
                                            query = ""
                                        } else {
                                            active = false
                                        }
                                        if (currentDestination?.hasRoute<EcranFilms>() == true) {
                                            viewModel.get_films_tendance()
                                        } else if (currentDestination?.hasRoute<EcranSeries>() == true) {
                                            viewModel.get_series_tendance()
                                        } else if (currentDestination?.hasRoute<EcranActeurs>() == true) {
                                            viewModel.get_acteurs_tendance()
                                        }
                                    },
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "icone fermeture"
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            query = query,
                            onQueryChange = { query = it },
                            onSearch = {
                                active = false

                                if (currentDestination?.hasRoute<EcranFilms>() == true) {
                                    viewModel.rechercherFilms(it)
                                } else if (currentDestination?.hasRoute<EcranSeries>() == true) {
                                    viewModel.rechercherSeries(it)
                                } else if (currentDestination?.hasRoute<EcranActeurs>() == true) {
                                    viewModel.rechercherActeurs(it)
                                }
                            },
                            active = active,
                            onActiveChange = { active = it },
                            placeholder = {
                                Text(text = "Rechercher")
                            }
                        ) {
                        }

                    }
                },
                bottomBar = {
                    if (currentDestination?.hasRoute<Acceuil>() != true) {
                        when (windowSizeClass.windowWidthSizeClass) {
                            WindowWidthSizeClass.COMPACT -> {
                                BottomNavBar(navController)
                            }
                        }
                    }
                },

            ) { innerPadding ->
                Row() {
                    if (currentDestination?.hasRoute<Acceuil>() != true) {
                        Column( modifier= Modifier.background(color = Color(0xFFefe9f4))) {
                            when (windowSizeClass.windowWidthSizeClass) {
                                WindowWidthSizeClass.EXPANDED -> {
                                    Spacer(modifier = Modifier.height(25.dp))
                                    SideNavBar(navController)
                                }
                            }
                        }
                    }
                Column(){
                NavHost(
                    navController = navController,
                    startDestination = Acceuil(),
                    Modifier.padding(innerPadding)
                ) {
                    composable<Acceuil> { Acceuil(navController, windowSizeClass) }
                    composable<EcranFilms> { EcranFilms(navController, viewModel, windowSizeClass) }
                    composable<EcranSeries> { EcranSeries(navController, viewModel, windowSizeClass) }
                    composable<EcranActeurs> { EcranActeurs(navController, viewModel, windowSizeClass) }
                    composable(
                        "DetailsFilm/{movieId}",
                        arguments = listOf(navArgument("movieId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val movieId = backStackEntry.arguments?.getInt("movieId")
                        movieId?.let {
                            DetailsFilm(
                                navController,
                                viewModel = viewModel,
                                id = it,
                                windowSizeClass
                            )
                        }
                    }

                    composable(
                        "DetailsSerie/{serieId}",
                        arguments = listOf(navArgument("serieId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val serieId = backStackEntry.arguments?.getInt("serieId")
                        serieId?.let {
                            DetailsSerie(
                                navController,
                                viewModel = viewModel,
                                id = it,
                                windowSizeClass
                            )
                        }
                    }

                    composable(
                        "DetailsActeur/{actorId}",
                        arguments = listOf(navArgument("actorId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val actorId = backStackEntry.arguments?.getInt("actorId")
                        actorId?.let {
                            DetailsActeur(
                                navController,
                                viewModel = viewModel,
                                id = it,
                                windowSizeClass
                            )
                        }
                    }
                }
                }
            }
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavController) {
    val destinations = listOf(Acceuil(), EcranFilms(), EcranSeries(), EcranActeurs())
    val labels = listOf("Accueil", "Films", "Séries", "Acteurs")
    val icons = listOf(R.drawable.home, R.drawable.movie, R.drawable.tv, R.drawable.person)
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route
    NavigationBar(
        //containerColor = Color(0xFF0c761d),
        contentColor = Color.Black
    ) {
        destinations.forEachIndexed { index, destination ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = icons[index]),
                        contentDescription = "",
                        modifier = Modifier.size(40.dp)
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
                    //indicatorColor =  Color(0xFFb3ff7a)
                )
            )
        }
    }
}

@Composable
fun SideNavBar(navController: NavController) {
    val destinations = listOf(Acceuil(), EcranFilms(), EcranSeries(), EcranActeurs())
    val labels = listOf("Accueil", "Films", "Séries", "Acteurs")
    val icons = listOf(R.drawable.home, R.drawable.movie, R.drawable.tv, R.drawable.person)
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    NavigationRail(
        containerColor = Color(0xFFefe9f4),
        contentColor = Color.Black,
        modifier = Modifier.fillMaxHeight()
    ) {
        destinations.forEachIndexed { index, destination ->
            NavigationRailItem(
                icon = {
                    Icon(
                        painter = painterResource(id = icons[index]),
                        contentDescription = "",
                        modifier = Modifier.size(40.dp)
                    )
                },
                label = { Text(labels[index]) },
                selected = currentRoute == destination,
                onClick = {
                    navController.navigate(destination)
                },
                alwaysShowLabel = true,
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Black,
                    unselectedTextColor = Color.Black,
                    selectedTextColor = Color.Black,
                    //indicatorColor =  Color(0xFFb3ff7a)
                )
            )
        }
    }
}


