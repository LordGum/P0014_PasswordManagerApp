package com.example.passwordmanagerapp.domain.usecases

import com.example.passwordmanagerapp.domain.repositories.RepositoryMain

class CheckMasterPasswordUseCase(
    private val repositoryMain: RepositoryMain
) {
    suspend operator fun invoke(masterPassword: String) = repositoryMain.checkMasterPassword(masterPassword)
}