package com.example.passwordmanagerapp.presentation.detail

import com.example.passwordmanagerapp.domain.entities.Website

sealed class DetailScreenState {
    data object Initial: DetailScreenState()

    data object AddState: DetailScreenState()

    data class RefactorState(
        val website: Website
    ) : DetailScreenState()


}