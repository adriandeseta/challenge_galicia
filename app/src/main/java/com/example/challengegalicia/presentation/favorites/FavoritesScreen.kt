package com.example.challengegalicia.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.challengegalicia.utils.Constants.DIVIDER_LINE_THICKNESS
import com.example.challengegalicia.utils.Constants.PADDING_8

@Composable
fun FavoritesScreen(favoritesViewModel: FavoritesViewModel) {
    val favorites = favoritesViewModel.favorites.collectAsState().value

    LazyColumn(
        Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        items(favorites.size) { index ->
            val user = favorites[index]
            FavListItem(user)
            Divider(
                color = Color.Gray,
                thickness = DIVIDER_LINE_THICKNESS,
                modifier = Modifier.padding(horizontal = PADDING_8)
            )
        }
    }
}
