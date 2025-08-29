package com.example.challengegalicia

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.challengegalicia.presentation.SharedUserViewModel
import com.example.challengegalicia.presentation.UserDetailScreen
import com.example.challengegalicia.presentation.UsersListScreen
import com.example.challengegalicia.presentation.UserListViewModel

@Composable
fun ChallengeNavigation(navController: NavHostController) {
    // Shared ViewModel con scope de Activity para compartir entre pantallas
    val sharedViewModel: SharedUserViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = MainScreens.UserList.route) {
        composable(MainScreens.UserList.route) {
            // Pasamos sharedViewModel y viewModel propio de la pantalla
            val viewModel: UserListViewModel = hiltViewModel()
            UsersListScreen(
                navController = navController,
                viewModel = viewModel,
                sharedViewModel = sharedViewModel
            )
        }

        composable(MainScreens.Detail.route) {
            // El detalle toma el mismo sharedViewModel
            UserDetailScreen(sharedViewModel = sharedViewModel)
        }
    }
}

sealed class MainScreens(val route: String) {
    data object UserList : MainScreens("user_list")
    data object Detail : MainScreens("user_detail")
}
