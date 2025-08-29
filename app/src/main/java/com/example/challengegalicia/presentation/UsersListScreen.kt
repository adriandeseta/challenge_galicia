package com.example.challengegalicia.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
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
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.example.challengegalicia.MainScreens
import com.example.challengegalicia.data.FavoriteUserEntity
import com.example.challengegalicia.presentation.model.UserModel
import com.example.challengegalicia.utils.CustomText
import com.example.challengegalicia.utils.orPlaceholder

@OptIn(ExperimentalMaterial3Api::class)
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
        // TopAppBar con botón de favoritos
        TopAppBar(
            title = { Text("Usuarios") },
            actions = {
                IconButton(onClick = {
                    navController.navigate(MainScreens.Favorites.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Favoritos"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color.Black,
                actionIconContentColor = Color.Black
            )
        )

        // Buscador
        OutlinedTextField(
            shape = RoundedCornerShape(50.dp),
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
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,
                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black.copy(0.4F)
            )
        )

        Box(modifier = Modifier.fillMaxSize()) {
            UserList(
                users = users,
                onItemClick = { clickedUser ->
                    sharedViewModel.selectUser(clickedUser)
                    navController.navigate(MainScreens.Detail.route)
                },
                onFavoriteClick = { user ->
                    val favUser = FavoriteUserEntity(
                        email = user.email,
                        firstName = user.name.firstName,
                        lastName = user.name.lastName,
                        country = user.country,
                        pictureUrl = user.picture.medium
                    )
                    favoritesViewModel.addFavorite(favUser)
                }
            )
        }
    }
}

@Composable
fun UserList(
    users: LazyPagingItems<UserModel>,
    onItemClick: (UserModel) -> Unit,
    onFavoriteClick: (UserModel) -> Unit
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
                        onFavoriteClick = { onFavoriteClick(user) }
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
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable { onClick() }
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
                    verticalArrangement = Arrangement.Center
                ) {
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = "Nombre: ${userModel.name.firstName.orPlaceholder("Desconocido")}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(4.dp))
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = "Apellido: ${userModel.name.lastName.orPlaceholder("Desconocido")}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(4.dp))
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = "País: ${userModel.country.orPlaceholder("Desconocido")}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(4.dp))
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = "Edad: ${userModel.dob.age.orPlaceholder("Desconocido")}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(4.dp))
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = "Email:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = userModel.email.orPlaceholder("Desconocido"),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Botón de favorito arriba a la derecha
            Button(
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(0.4f),
                    contentColor = Color.Black
                ),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                onClick = onFavoriteClick
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorito",
                    modifier = Modifier.size(25.dp),
                    tint = Color.Black
                )
            }
        }
    }
}
