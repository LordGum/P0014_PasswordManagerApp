package com.example.passwordmanagerapp.domain.usecases.enter

import com.example.passwordmanagerapp.domain.repositories.RepositoryEnter
import javax.inject.Inject

class CheckIsMasterPasswordNullUseCase @Inject constructor(
    private val repository: RepositoryEnter
) {
    operator fun invoke() = repository.checkIsMasterPasswordNull()
}