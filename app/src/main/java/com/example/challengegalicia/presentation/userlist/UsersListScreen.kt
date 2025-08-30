package com.example.challengegalicia.presentation.userlist

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.example.challengegalicia.MainScreens
import com.example.challengegalicia.data.FavoriteUserEntity
import com.example.challengegalicia.presentation.favorites.FavoritesViewModel
import com.example.challengegalicia.presentation.SharedUserViewModel
import com.example.challengegalicia.presentation.model.UserModel
import com.example.challengegalicia.utils.CustomText
import com.example.challengegalicia.utils.orPlaceholder

@Composable
fun UsersListScreen(
    viewModel: UserListViewModel,
    sharedViewModel: SharedUserViewModel,
    favoritesViewModel: FavoritesViewModel,
    navController: NavHostController
) {
    val users = viewModel.users.collectAsLazyPagingItems()
    val searchQuery = viewModel.searchQuery.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        OutlinedTextField(
            shape = RoundedCornerShape(12.dp),
            value = searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            placeholder = { Text(text = "Buscar por país o nombre...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White.copy(0.4F),
                unfocusedContainerColor = Color.White.copy(0.4F),
                focusedIndicatorColor = Color(0xFF001F5B),
                unfocusedIndicatorColor = Color(0xFF001F5B),
                cursorColor = Color(0xFF001F5B),
                focusedTextColor = Color(0xFF001F5B),
                unfocusedTextColor = Color(0xFF001F5B).copy(0.4F)
            )
        )

        Box(modifier = Modifier.fillMaxSize()) {
            UserList(
                users = users,
                onItemClick = { clickedUser ->
                    sharedViewModel.selectUser(clickedUser)
                    navController.navigate(MainScreens.Detail.route)
                },
                favoritesViewModel = favoritesViewModel
            )
        }
    }
}

@Composable
fun UserList(
    users: LazyPagingItems<UserModel>,
    onItemClick: (UserModel) -> Unit,
    favoritesViewModel: FavoritesViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (users.itemCount == 0) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No se encontraron usuarios o aún cargando...",
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            items(count = users.itemCount) { index ->
                users[index]?.let { user ->
                    ListItem(
                        userModel = user,
                        onClick = { onItemClick(user) },
                        favoritesViewModel = favoritesViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun ListItem(
    userModel: UserModel,
    onClick: () -> Unit,
    favoritesViewModel: FavoritesViewModel
) {
    val context = LocalContext.current
    val isFavorite = favoritesViewModel.isFavorite(userModel.email).collectAsState(initial = false)

    Card(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .shadow(8.dp, RoundedCornerShape(12.dp))
            .height(200.dp),
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
                    painter = rememberImagePainter(data = userModel.picture.medium),
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
                    verticalArrangement = Arrangement.Top
                ) {
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = "Nombre",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = userModel.name.firstName.orPlaceholder("Desconocido"),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )

                    Spacer(Modifier.height(4.dp))
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = "Apellido",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = userModel.name.lastName.orPlaceholder("Desconocido"),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )

                    Spacer(Modifier.height(4.dp))
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = "País",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = userModel.country.orPlaceholder("Desconocido"),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )

                    Spacer(Modifier.height(4.dp))
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = "Edad",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = userModel.dob.age.orPlaceholder("Desconocido"),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )

                    Spacer(Modifier.height(4.dp))
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = "Email",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = userModel.email.orPlaceholder("xxxx@xxxx.xxx"),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )

                }
            }

            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                onClick = {
                    if (!isFavorite.value) {
                        val favUser = FavoriteUserEntity(
                            email = userModel.email,
                            firstName = userModel.name.firstName,
                            lastName = userModel.name.lastName,
                            country = userModel.country,
                            pictureUrl = userModel.picture.medium,
                            age = userModel.dob.age.toString(),
                            phone = userModel.phone,
                        )
                        favoritesViewModel.addFavorite(favUser)
                        Toast.makeText(
                            context,
                            "El usuario fue agregado a favoritos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ) {
                Icon(
                    imageVector = if (isFavorite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorito",
                    tint = if (isFavorite.value) Color.Red else Color.Gray
                )
            }
        }
    }
}
