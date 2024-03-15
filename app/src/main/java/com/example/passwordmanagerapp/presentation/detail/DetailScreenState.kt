package com.example.passwordmanagerapp.presentation.detail

sealed class DetailScreenState {
    data object Initial: DetailScreenState()

    data object AddState: DetailScreenState()

    data object RefactorState: DetailScreenState()
}