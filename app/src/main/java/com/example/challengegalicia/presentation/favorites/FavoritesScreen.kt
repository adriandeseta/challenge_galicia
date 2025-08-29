package com.example.challengegalicia.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Image(
//                    painter = rememberImagePainter(data = user.pictureUrl),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(60.dp)
//                        .clip(RoundedCornerShape(8.dp))
//                )
//                Spacer(Modifier.width(12.dp))
//                Column {
//                    Text("${user.firstName} ${user.lastName}")
//                    Text(user.country.orEmpty())
//                    Text(user.email)
//                }
//            }
        }
    }
}

@Composable
fun FavListItem(user: FavoriteUserEntity) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .shadow(8.dp, RoundedCornerShape(12.dp))
            .height(170.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberImagePainter(data = user.pictureUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .width(100.dp)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = "Nombre: ${user.firstName.orPlaceholder("Desconocido")}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(4.dp))
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = "Apellido: ${user.lastName.orPlaceholder("Desconocido")}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(4.dp))
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = "País: ${user.country.orPlaceholder("Desconocido")}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

        }
    }
}
