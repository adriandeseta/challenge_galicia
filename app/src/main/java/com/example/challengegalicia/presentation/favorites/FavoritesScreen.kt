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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.challengegalicia.data.FavoriteUserEntity
import com.example.challengegalicia.utils.CustomText
import com.example.challengegalicia.utils.orPlaceholder

@Composable
fun FavoritesScreen(favoritesViewModel: FavoritesViewModel) {
    val favorites = favoritesViewModel.favorites.collectAsState().value

    LazyColumn(Modifier.background(Color.White).fillMaxSize()) {
        items(favorites.size) { index ->
            val user = favorites[index]
            FavListItem(user)
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun FavListItem(user: FavoriteUserEntity) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
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
                        .size(70.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                CustomText(
                    text = "${user.firstName.orPlaceholder("Desconocido")} ${
                        user.lastName.orPlaceholder("Desconocido")
                    }",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(12.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                Spacer(modifier = Modifier.height(4.dp))

                CustomText(
                    annotatedText = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) {
                            append("Edad: ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, color = Color.Black)) {
                            append(user.age.orPlaceholder("Desconocido"))
                        }
                    },
                    fontSize = 14.sp
                )
                CustomText(
                    annotatedText = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) {
                            append("País: ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, color = Color.Black)) {
                            append(user.country.orPlaceholder("Desconocido"))
                        }
                    },
                    fontSize = 14.sp
                )
                CustomText(
                    annotatedText = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) {
                            append("Email: ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, color = Color.Black)) {
                            append(user.email.orPlaceholder("xxxx@xxxx.xxx"))
                        }
                    },
                    fontSize = 14.sp
                )
            }

        }
    }
}