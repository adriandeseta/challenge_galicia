package com.example.challengegalicia

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.challengegalicia.presentation.favorites.FavoritesScreen
import com.example.challengegalicia.presentation.favorites.FavoritesViewModel
import com.example.challengegalicia.presentation.SharedUserViewModel
import com.example.challengegalicia.presentation.userdetail.UserDetailScreen
import com.example.challengegalicia.presentation.userlist.UserListViewModel
import com.example.challengegalicia.presentation.userlist.UsersListScreen

@Composable
fun ChallengeNavigation(
    navController: NavHostController,
    userListViewModel: UserListViewModel = hiltViewModel(),
    sharedViewModel: SharedUserViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = MainScreens.UserList.route
    ) {
        composable(MainScreens.UserList.route) {
            UsersListScreen(
                viewModel = userListViewModel,
                sharedViewModel = sharedViewModel,
                navController = navController,
                favoritesViewModel = favoritesViewModel
            )
        }
        composable(MainScreens.Detail.route) {
            UserDetailScreen(sharedViewModel)
        }
        composable(MainScreens.Favorites.route) {
            FavoritesScreen(favoritesViewModel)
        }
    }
}
sealed class MainScreens(val route: String) {
    data object UserList : MainScreens("user_list")
    data object Detail : MainScreens("user_detail")
    data object Favorites : MainScreens("favorites")
}
