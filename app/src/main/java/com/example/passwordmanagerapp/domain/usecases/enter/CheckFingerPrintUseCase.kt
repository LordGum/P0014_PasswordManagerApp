package com.example.passwordmanagerapp.domain.usecases.enter

import com.example.passwordmanagerapp.domain.repositories.RepositoryEnter
import javax.inject.Inject

class CheckFingerPrintUseCase @Inject constructor(
    private val repositoryMain: RepositoryEnter
) {
    suspend operator fun invoke() = repositoryMain.checkFingerPrint()
}