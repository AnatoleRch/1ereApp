package com.example.a1ereapp

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Exam(viewModel: MainViewModel) {
    val collections by viewModel.collections.collectAsState()

    LaunchedEffect(true) {
        viewModel.get_collection_horror()
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier.padding(8.dp),
    ) {
        items(collections) { collection ->
            Text(text = collection.name)


        }
    }
}