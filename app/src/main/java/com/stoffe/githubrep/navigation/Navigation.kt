package com.stoffe.githubrep.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.stoffe.githubrep.ui.screen.detail.UserDetailScreen
import com.stoffe.githubrep.ui.screen.list.UserListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.LIST
    ) {
        composable(Route.LIST) {
            UserListScreen(
                onUserClick = { username ->
                    navController.navigate("${Route.DETAIL}/$username")
                }
            )
        }
        composable(
            route = "${Route.DETAIL}/{${Argument.USERNAME}}",
            arguments = listOf(
                navArgument(Argument.USERNAME) {
                    type = NavType.StringType
                },
            )
        ) {
            UserDetailScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}