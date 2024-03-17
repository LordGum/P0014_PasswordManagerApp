package com.example.passwordmanagerapp.presentation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.example.passwordmanagerapp.navigation.AppNavGraph
import com.example.passwordmanagerapp.navigation.Screen
import com.example.passwordmanagerapp.navigation.rememberNavigationState
import com.example.passwordmanagerapp.presentation.detail.DetailScreen
import com.example.passwordmanagerapp.presentation.detail.DetailViewModel
import com.example.passwordmanagerapp.presentation.enter.EnterScreen
import com.example.passwordmanagerapp.presentation.enter.EnterViewModel
import com.example.passwordmanagerapp.presentation.enter.ui_enter.WelcomeFingerPrintScreen
import com.example.passwordmanagerapp.presentation.main.MainScreen
import com.example.passwordmanagerapp.presentation.main.MainViewModel

@Composable
fun BaseScreen() {
    val navigationState = rememberNavigationState()
    val component = getApplicationComponent()

    AppNavGraph(
        navHostController = navigationState.navHostController,
        enterScreenContent = {
            val viewModel: EnterViewModel = ViewModelProvider(
                LocalContext.current as ComponentActivity,
                component.getViewModelFactory()
            )[EnterViewModel::class.java]
            EnterScreen(
                onAddFingerPrint = {
                    navigationState.navigateTo(Screen.FingerPrintScreen.route)
                },
                onClickListener = {
                    navigationState.navigateTo(Screen.MainScreen.route)
                },
                viewModel
            )
        },
        mainScreenContent = {
            val viewModel: MainViewModel = ViewModelProvider(
                LocalContext.current as ComponentActivity,
                component.getViewModelFactory()
            )[MainViewModel::class.java]
            MainScreen(
                viewModel,
                onWebsiteClickListener = {
                    navigationState.navigateToDetailScreen(it)
                }
            )
        },
        detailScreenContent = { website ->
            val viewModel: DetailViewModel = ViewModelProvider(
                LocalContext.current as ComponentActivity,
                component.getViewModelFactory()
            )[DetailViewModel::class.java]
            DetailScreen(
                viewModel,
                website,
                onBackIconClick = {
                    navigationState.navHostController.popBackStack()
                }
            )
        },
        fingerScreenContent = {
            WelcomeFingerPrintScreen (
                onNoClick = {
                    navigationState.navigateTo(Screen.MainScreen.route)
                },
                onSuccess = {
                    navigationState.navigateTo(Screen.MainScreen.route)
                }
            )
        }
    )
}