package com.example.passwordmanagerapp.domain.usecases

import com.example.passwordmanagerapp.domain.repositories.RepositoryMain

class ResetMasterPasswordUseCase(
    private val repositoryMain: RepositoryMain
) {
    suspend operator fun invoke() = repositoryMain.resetMasterPassword()
}