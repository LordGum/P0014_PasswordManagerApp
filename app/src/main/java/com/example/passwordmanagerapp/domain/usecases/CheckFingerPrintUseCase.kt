package com.example.passwordmanagerapp.domain.usecases

import com.example.passwordmanagerapp.domain.repositories.RepositoryMain

class CheckFingerPrintUseCase(
    private val repositoryMain: RepositoryMain
) {
    suspend operator fun invoke() = repositoryMain.checkFingerPrint()
}