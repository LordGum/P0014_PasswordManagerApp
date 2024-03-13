package com.example.passwordmanagerapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    enterScreenContent: @Composable () -> Unit,
    mainScreenContent: @Composable () -> Unit,
    detailScreenContent: @Composable (Int) -> Unit,

    ) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.EnterScreen.route  //TODO(поменять на EnterScreen)
    ) {
        composable(Screen.EnterScreen.route) {
            enterScreenContent()
        }
        composable(Screen.MainScreen.route) {
            mainScreenContent()
        }
        composable(
            route = Screen.DetailScreen.route,
            arguments = listOf(
                navArgument(Screen.WEBSITE_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            val id = it.arguments?.getInt(Screen.WEBSITE_ID) ?: -1
            detailScreenContent(id)
        }
    }
}