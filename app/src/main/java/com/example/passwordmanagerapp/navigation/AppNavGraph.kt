package com.example.passwordmanagerapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    enterScreenContent: @Composable () -> Unit,
    mainScreenContent: @Composable () -> Unit,
    detailScreenContent: @Composable () -> Unit,

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
        composable(Screen.DetailScreen.route) {
            detailScreenContent()
        }
    }
}