package com.example.passwordmanagerapp.domain.usecases.enter

import com.example.passwordmanagerapp.domain.repositories.RepositoryEnter
import javax.inject.Inject

class CheckMasterPasswordUseCase @Inject constructor(
    private val repositoryMain: RepositoryEnter
) {
    suspend operator fun invoke(masterPassword: String) = repositoryMain.checkMasterPassword(masterPassword)
}