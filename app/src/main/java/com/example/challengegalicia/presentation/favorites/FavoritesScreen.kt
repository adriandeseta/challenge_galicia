package com.example.challengegalicia.presentation.favorites

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.challengegalicia.R
import com.example.challengegalicia.data.local.FavoriteUserEntity
import com.example.challengegalicia.utils.Constants.DIVIDER_LINE_THICKNESS
import com.example.challengegalicia.utils.Constants.FONT_SIZE_TITLE_18
import com.example.challengegalicia.utils.Constants.LINE_HEIGHT
import com.example.challengegalicia.utils.Constants.PADDING_24
import com.example.challengegalicia.utils.Constants.PADDING_8
import com.example.challengegalicia.utils.Constants.SPACER_18
import com.example.challengegalicia.utils.Constants.SPACER_200
import com.example.challengegalicia.utils.CustomText

@Composable
fun FavoritesScreen(favoritesViewModel: FavoritesViewModel) {
    val state by favoritesViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        when (state) {
            is FavoritesUiState.Idle -> { /* Nada o placeholder */
            }

            is FavoritesUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is FavoritesUiState.Error -> {
                CustomText(
                    text = (state as FavoritesUiState.Error).message,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is FavoritesUiState.Success -> {
                val favorites = (state as FavoritesUiState.Success).favorites
                if (favorites.isEmpty()) {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn()
                    ) {

                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Spacer(modifier = Modifier.height(SPACER_200))

                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                modifier = Modifier.size(90.dp),
                                tint = Color.LightGray
                            )
                            Spacer(modifier = Modifier.height(SPACER_18))
                            CustomText(
                                modifier = Modifier.padding(horizontal = PADDING_24),
                                text = stringResource(R.string.empty_favorites_message),
                                color = Color.Gray,
                                textAlign = TextAlign.Center,
                                fontSize = FONT_SIZE_TITLE_18,
                                style = TextStyle(
                                    lineHeight = LINE_HEIGHT
                                )
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
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
            }
        }
    }
}

sealed class FavoritesUiState {
    data object Idle : FavoritesUiState()
    data object Loading : FavoritesUiState()
    data class Success(val favorites: List<FavoriteUserEntity>) : FavoritesUiState()
    data class Error(val message: String) : FavoritesUiState()
}