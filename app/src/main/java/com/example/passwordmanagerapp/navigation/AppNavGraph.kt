package com.example.passwordmanagerapp.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.passwordmanagerapp.domain.entities.Website
import com.google.gson.Gson

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    enterScreenContent: @Composable () -> Unit,
    mainScreenContent: @Composable () -> Unit,
    detailScreenContent: @Composable (Website) -> Unit,
    fingerScreenContent: @Composable () -> Unit
    ) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.EnterScreen.route
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
                navArgument(Screen.WEBSITE) {
                    type = NavType.StringType
                }
            )
        ) {
            val websiteJson = it.arguments?.getString(Screen.WEBSITE) ?: ""
            val website = Gson().fromJson(websiteJson, Website::class.java)
            detailScreenContent(website)
        }

        composable(Screen.FingerPrintScreen.route) {
            fingerScreenContent()
        }
    }
}