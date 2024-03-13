package com.example.passwordmanagerapp.presentation.enter

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.example.passwordmanagerapp.navigation.AppNavGraph
import com.example.passwordmanagerapp.navigation.Screen
import com.example.passwordmanagerapp.navigation.rememberNavigationState
import com.example.passwordmanagerapp.presentation.detail.DetailScreen
import com.example.passwordmanagerapp.presentation.detail.DetailViewModel
import com.example.passwordmanagerapp.presentation.main.MainScreen
import com.example.passwordmanagerapp.presentation.main.MainViewModel

@Composable
fun BaseScreen() {
    val navigationState = rememberNavigationState()

    AppNavGraph(
        navHostController = navigationState.navHostController,
        enterScreenContent = {
            EnterScreen(
                onClickListener = {
                    navigationState.navigateTo(Screen.MainScreen.route)
                }
            )
        },
        mainScreenContent = {
            val viewModel: MainViewModel = ViewModelProvider(LocalContext.current as ComponentActivity)[MainViewModel::class.java]
            MainScreen(
                viewModel,
                onWebsiteClickListener = {
                    navigationState.navigateToDetailScreen(it)
                }
            )
        },
        detailScreenContent = {
            val viewModel: DetailViewModel = ViewModelProvider(LocalContext.current as ComponentActivity)[DetailViewModel::class.java]
            DetailScreen(
                viewModel,
                it,
                onBackIconClick = {
                    navigationState.navHostController.popBackStack()
                }
            )
        }
    )
}