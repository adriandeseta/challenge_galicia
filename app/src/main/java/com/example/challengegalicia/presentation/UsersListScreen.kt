package com.example.challengegalicia.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.challengegalicia.presentation.model.UserModel

@Composable
fun UsersListScreen(viewModel: UserListViewModel = hiltViewModel()){
    val users = viewModel.users.collectAsLazyPagingItems()
    UserList(users)

}

@Composable
fun UserList(users:LazyPagingItems<UserModel>){
    LazyColumn {
        items(users.itemCount){
            users[it]?.let { userModel ->
                ItemList(userModel)
            }
        }
    }
}
@Composable
fun ItemList(userModel: UserModel){
    Box(Modifier.fillMaxWidth().height(200.dp).background(Color.Cyan).padding(20.dp)){
        Text(text = userModel.name.firstName)
    }
}