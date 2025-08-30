package com.example.challengegalicia.presentation.userlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.challengegalicia.MainScreens
import com.example.challengegalicia.R
import com.example.challengegalicia.presentation.SharedUserViewModel
import com.example.challengegalicia.presentation.favorites.FavoritesViewModel
import com.example.challengegalicia.presentation.model.UserModel
import com.example.challengegalicia.utils.Constants.DIVIDER_LINE_THICKNESS
import com.example.challengegalicia.utils.Constants.FONT_SIZE_TITLE_16
import com.example.challengegalicia.utils.Constants.PADDING_24
import com.example.challengegalicia.utils.Constants.PADDING_8
import com.example.challengegalicia.utils.Constants.ROUND_CORNER_12
import com.example.challengegalicia.utils.figtreeFontFamily

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
                        text = stringResource(R.string.user_list_empty_state_title),
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
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
        }
    }
}
