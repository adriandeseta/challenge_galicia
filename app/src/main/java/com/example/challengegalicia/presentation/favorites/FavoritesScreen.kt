package com.example.challengegalicia.presentation.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.challengegalicia.data.FavoriteUserEntity
import com.example.challengegalicia.utils.Constants.DIVIDER_LINE_THICKNESS
import com.example.challengegalicia.utils.Constants.FONT_SIZE_TITLE_16
import com.example.challengegalicia.utils.Constants.FONT_SIZE_TITLE_24
import com.example.challengegalicia.utils.Constants.PADDING_24
import com.example.challengegalicia.utils.Constants.PADDING_8
import com.example.challengegalicia.utils.Constants.SPACER_18
import com.example.challengegalicia.utils.Constants.SPACER_4
import com.example.challengegalicia.utils.Constants.USER_LIST_IMAGE_SIZE
import com.example.challengegalicia.utils.CustomText
import com.example.challengegalicia.utils.orPlaceholder

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

@Composable
fun FavListItem(user: FavoriteUserEntity) {

    Column(
        modifier = Modifier
            .padding(PADDING_24)
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberImagePainter(data = user.pictureUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(USER_LIST_IMAGE_SIZE)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(SPACER_18))

                CustomText(
                    text = "${user.firstName.orPlaceholder("Desconocido")}\n${
                        user.lastName.orPlaceholder("Desconocido")
                    }",
                    fontSize = FONT_SIZE_TITLE_24,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                )
            }

        }
        Spacer(modifier = Modifier.height(SPACER_18))

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                CustomText(
                    annotatedText = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        ) {
                            append("Edad: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = Color.Black
                            )
                        ) {
                            append(user.age.orPlaceholder("Desconocido"))
                        }
                    },
                    fontSize = FONT_SIZE_TITLE_16
                )

                Spacer(modifier = Modifier.height(SPACER_4))

                CustomText(
                    annotatedText = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        ) {
                            append("País: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = Color.Black
                            )
                        ) {
                            append(user.country.orPlaceholder("Desconocido"))
                        }
                    },
                    fontSize = FONT_SIZE_TITLE_16
                )

                Spacer(modifier = Modifier.height(SPACER_4))

                CustomText(
                    annotatedText = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        ) {
                            append("Email: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = Color.Black
                            )
                        ) {
                            append(user.email.orPlaceholder("xxxx@xxxx.xxx"))
                        }
                    },
                    fontSize = FONT_SIZE_TITLE_16
                )
            }

        }
    }
}