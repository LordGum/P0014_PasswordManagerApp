package com.example.passwordmanagerapp.domain.usecases.main

import com.example.passwordmanagerapp.domain.repositories.RepositoryMain
import javax.inject.Inject

class ResetMasterPasswordUseCase @Inject constructor(
    private val repositoryMain: RepositoryMain
) {
    suspend operator fun invoke() = repositoryMain.resetMasterPassword()
}