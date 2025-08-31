package com.example.challengegalicia.presentation.userlist

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.challengegalicia.MainScreens
import com.example.challengegalicia.R
import com.example.challengegalicia.presentation.SharedUserViewModel
import com.example.challengegalicia.presentation.favorites.FavoritesViewModel
import com.example.challengegalicia.presentation.model.UserModel
import com.example.challengegalicia.utils.Constants.DIVIDER_LINE_THICKNESS
import com.example.challengegalicia.utils.Constants.FONT_SIZE_TITLE_16
import com.example.challengegalicia.utils.Constants.FONT_SIZE_TITLE_18
import com.example.challengegalicia.utils.Constants.LINE_HEIGHT
import com.example.challengegalicia.utils.Constants.PADDING_16
import com.example.challengegalicia.utils.Constants.PADDING_24
import com.example.challengegalicia.utils.Constants.PADDING_8
import com.example.challengegalicia.utils.Constants.ROUND_CORNER_12
import com.example.challengegalicia.utils.Constants.SPACER_12
import com.example.challengegalicia.utils.Constants.SPACER_18
import com.example.challengegalicia.utils.Constants.SPACER_4
import com.example.challengegalicia.utils.CustomText
import com.example.challengegalicia.utils.figtreeFontFamily
import kotlinx.coroutines.flow.Flow

@Composable
fun UsersListScreen(
    viewModel: UserListViewModel,
    sharedViewModel: SharedUserViewModel,
    favoritesViewModel: FavoritesViewModel,
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(SPACER_18))

        OutlinedTextField(
            shape = RoundedCornerShape(ROUND_CORNER_12),
            value = searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            placeholder = { Text(text = stringResource(id = R.string.user_list_search_placeholder)) },
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
        Spacer(modifier = Modifier.height(SPACER_18))
        Box(modifier = Modifier.fillMaxSize()) {
            when (val state = uiState) {
                is UserListUiState.Idle -> {
                    // Poner pantalla default
                }

                is UserListUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is UserListUiState.Success -> {
                    val users = state.usersFlow.collectAsLazyPagingItems()
                    UserList(
                        users = users,
                        onItemClick = { clickedUser ->
                            sharedViewModel.selectUser(clickedUser)
                            navController.navigate(MainScreens.Detail.route)
                        },
                        favoritesViewModel = favoritesViewModel
                    )
                }

                is UserListUiState.Error -> {
                    Text(
                        text = state.message,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun UserList(
    users: LazyPagingItems<UserModel>,
    onItemClick: (UserModel) -> Unit,
    favoritesViewModel: FavoritesViewModel
) {
    val loadState = users.loadState

    when {
        loadState.refresh is LoadState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(PADDING_24),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        loadState.refresh is LoadState.Error -> {
            val error = (loadState.refresh as LoadState.Error).error
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(PADDING_24),
                contentAlignment = Alignment.Center
            ) {
                CustomText(
                    text = "${stringResource(R.string.user_list_error_title)} ${
                        error.localizedMessage ?: stringResource(R.string.user_list_error_message)
                    }",
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
            }
        }

        users.itemCount == 0 -> {
            Spacer(modifier = Modifier.height(SPACER_12))

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = PADDING_24),
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(SPACER_4))
                    CustomText(
                        text = stringResource(R.string.user_list_empty_state_title),
                        color = Color.Gray,
                        textAlign = TextAlign.Start,
                        fontSize = FONT_SIZE_TITLE_18,
                        style = TextStyle(
                            lineHeight = LINE_HEIGHT
                        )
                    )
                }
            }

        }

        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(count = users.itemCount) { index ->
                    users[index]?.let { user ->
                        UserListItem(
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

                if (loadState.append is LoadState.Loading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(PADDING_16),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                if (loadState.append is LoadState.Error) {
                    val appendError = (loadState.append as LoadState.Error).error
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(PADDING_16),
                            contentAlignment = Alignment.Center
                        ) {
                            CustomText(
                                text = "Error al cargar más: ${appendError.localizedMessage}",
                                color = Color.Red,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed class UserListUiState {
    object Idle : UserListUiState()
    object Loading : UserListUiState()
    data class Success(val usersFlow: Flow<PagingData<UserModel>>) : UserListUiState()
    data class Error(val message: String) : UserListUiState()
}