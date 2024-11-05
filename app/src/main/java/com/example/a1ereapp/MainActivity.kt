package com.example.a1ereapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument

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
                topBar = {
                    if (currentRoute != "Acceuil"){
                    }
                },
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

@Composable
fun BottomNavBar(navController: NavController) {
    val destinations = listOf("Acceuil", "EcranFilms", "EcranSeries", "EcranActeurs")
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
                    //indicatorColor =  Color(0xFFb3ff7a)
                )
            )
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
) {
    var isSearching by remember { mutableStateOf(false) }

    TextField(
        value = query,
        onValueChange = { newValue ->
            onQueryChange(newValue)
            isSearching = newValue.isNotEmpty()
        },

        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        placeholder = { Text("Rechercher...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            if (isSearching) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear Search"
                    )
                }
            }
        },
        singleLine = true,
    )
}
