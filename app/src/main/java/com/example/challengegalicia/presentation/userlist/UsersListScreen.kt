package com.example.challengegalicia.presentation.userlist

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.example.challengegalicia.MainScreens
import com.example.challengegalicia.data.FavoriteUserEntity
import com.example.challengegalicia.presentation.SharedUserViewModel
import com.example.challengegalicia.presentation.favorites.FavoritesViewModel
import com.example.challengegalicia.presentation.model.UserModel
import com.example.challengegalicia.utils.Constants.DIVIDER_LINE_THICKNESS
import com.example.challengegalicia.utils.Constants.FONT_SIZE_TITLE_16
import com.example.challengegalicia.utils.Constants.FONT_SIZE_TITLE_24
import com.example.challengegalicia.utils.Constants.PADDING_24
import com.example.challengegalicia.utils.Constants.PADDING_8
import com.example.challengegalicia.utils.Constants.ROUND_CORNER_12
import com.example.challengegalicia.utils.Constants.SPACER_18
import com.example.challengegalicia.utils.Constants.SPACER_4
import com.example.challengegalicia.utils.Constants.USER_LIST_IMAGE_SIZE
import com.example.challengegalicia.utils.CustomText
import com.example.challengegalicia.utils.figtreeFontFamily
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
            shape = RoundedCornerShape(ROUND_CORNER_12),
            value = searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            placeholder = { Text(text = "Buscar por país o nombre...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(PADDING_8),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White.copy(0.4F),
                unfocusedContainerColor = Color.White.copy(0.4F),
                focusedIndicatorColor = Color(0xFF001F5B),
                unfocusedIndicatorColor = Color(0xFF001F5B),
                cursorColor = Color(0xFF001F5B),
                focusedTextColor = Color(0xFF001F5B),
                unfocusedTextColor = Color(0xFF001F5B).copy(0.4F)
            ),
            textStyle = TextStyle(
                fontFamily = figtreeFontFamily,
                fontSize = FONT_SIZE_TITLE_16
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
                        .padding(PADDING_24),
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

@Composable
fun ListItem(
    userModel: UserModel,
    onClick: () -> Unit,
    favoritesViewModel: FavoritesViewModel
) {
    val context = LocalContext.current
    val isFavorite = favoritesViewModel.isFavorite(userModel.email).collectAsState(initial = false)

    Column(
        modifier = Modifier
            .clickable { onClick() }
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
                    painter = rememberImagePainter(data = userModel.picture.medium),
                    contentDescription = null,
                    modifier = Modifier
                        .size(USER_LIST_IMAGE_SIZE)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(SPACER_18))

                CustomText(
                    text = "${userModel.name.firstName.orPlaceholder("Desconocido")}\n${
                        userModel.name.lastName.orPlaceholder("Desconocido")
                    }",
                    fontSize = FONT_SIZE_TITLE_24,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                )
            }

            IconButton(
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
                            append(userModel.dob.age.orPlaceholder("Desconocido"))
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
                            append(userModel.country.orPlaceholder("Desconocido"))
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
                            append(userModel.email.orPlaceholder("xxxx@xxxx.xxx"))
                        }
                    },
                    fontSize = FONT_SIZE_TITLE_16
                )
            }

        }
    }
}