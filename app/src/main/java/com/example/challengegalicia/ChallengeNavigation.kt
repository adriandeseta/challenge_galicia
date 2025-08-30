package com.example.challengegalicia

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.challengegalicia.presentation.SharedUserViewModel
import com.example.challengegalicia.presentation.favorites.FavoritesScreen
import com.example.challengegalicia.presentation.favorites.FavoritesViewModel
import com.example.challengegalicia.presentation.userdetail.UserDetailScreen
import com.example.challengegalicia.presentation.userlist.UserListViewModel
import com.example.challengegalicia.presentation.userlist.UsersListScreen
import com.example.challengegalicia.ui.theme.azulOscuro
import com.example.challengegalicia.utils.Constants.DIVIDER_LINE_THICKNESS
import com.example.challengegalicia.utils.Constants.FONT_SIZE_TITLE_24
import com.example.challengegalicia.utils.Constants.PADDING_8
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
        Column(modifier = Modifier.padding(innerPadding)) {
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
                    UserDetailScreen(
                        sharedViewModel = sharedViewModel,
                        favoritesViewModel = favoritesViewModel
                    )
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

    TopAppBar(
        title = {
            CustomText(
                text = when (currentRoute) {
                    MainScreens.UserList.route -> stringResource(R.string.app_bar_user_title)
                    MainScreens.Detail.route -> stringResource(R.string.app_bar_profile_title)
                    MainScreens.Favorites.route -> stringResource(R.string.app_bar_fav_title)
                    else -> ""
                },
                color = Color.White,
                fontSize = FONT_SIZE_TITLE_24

            )
        },
        navigationIcon = {
            if (currentRoute != MainScreens.UserList.route) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.app_bar_back_button_content_description),
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
                    border = BorderStroke(DIVIDER_LINE_THICKNESS, Color.White),
                    modifier = Modifier.padding(end = PADDING_8)

                ) {
                    Text(stringResource(R.string.app_bar_button_title))
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
