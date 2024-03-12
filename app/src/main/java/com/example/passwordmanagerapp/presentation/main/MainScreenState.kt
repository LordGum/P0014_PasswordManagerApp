package com.example.passwordmanagerapp.presentation.main

import com.example.passwordmanagerapp.domain.entities.Website

sealed class MainScreenState {
    object Initial: MainScreenState()

    data class WebsiteList(
        val websiteList: List<Website>
    ): MainScreenState()
}