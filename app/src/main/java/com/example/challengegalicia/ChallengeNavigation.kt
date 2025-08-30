package com.example.challengegalicia

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.challengegalicia.presentation.favorites.FavoritesScreen
import com.example.challengegalicia.presentation.favorites.FavoritesViewModel
import com.example.challengegalicia.presentation.SharedUserViewModel
import com.example.challengegalicia.presentation.userdetail.UserDetailScreen
import com.example.challengegalicia.presentation.userlist.UserListViewModel
import com.example.challengegalicia.presentation.userlist.UsersListScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challengegalicia.utils.Constants.ROUND_CORNER_12
import com.example.challengegalicia.utils.CustomText

@Composable
fun ChallengeNavigation(
    navController: NavHostController,
    userListViewModel: UserListViewModel = hiltViewModel(),
    sharedViewModel: SharedUserViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        topBar = {
            CommonTopBar(
                navController = navController,
                currentRoute = currentRoute
            )
        }
    ) { innerPadding ->
        Column(modifier = androidx.compose.ui.Modifier.padding(innerPadding)) {
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopBar(
    navController: NavHostController,
    currentRoute: String?
) {
    val azulOscuro = Color(0xFF001F5B)

    TopAppBar(
        title = {
            CustomText(
                text = when (currentRoute) {
                    MainScreens.UserList.route -> "Usuarios"
                    MainScreens.Detail.route -> "Perfil"
                    MainScreens.Favorites.route -> "Favoritos"
                    else -> ""
                },
                color = Color.White,
                fontSize = 24.sp

            )
        },
        navigationIcon = {
            if (currentRoute != MainScreens.UserList.route) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }
            }
        },
        actions = {
            if (currentRoute == MainScreens.UserList.route || currentRoute == MainScreens.Detail.route) {
                Button(
                    onClick = { navController.navigate(MainScreens.Favorites.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = azulOscuro,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(ROUND_CORNER_12),
                    elevation = ButtonDefaults.buttonElevation(4.dp),
                    border = BorderStroke(1.dp, Color.White),
                    modifier = Modifier.padding(end = 8.dp) // 👈 acá el padding derecho

                ) {
                    Text("Ir a favoritos")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = azulOscuro,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}

sealed class MainScreens(val route: String) {
    data object UserList : MainScreens("user_list")
    data object Detail : MainScreens("user_detail")
    data object Favorites : MainScreens("favorites")
}
