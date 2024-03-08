package com.example.passwordmanagerapp.domain.usecases

import com.example.passwordmanagerapp.domain.repositories.RepositoryAccount

class GetListAccountUseCase(
    private val repositoryAccount: RepositoryAccount
) {
    operator fun invoke() = repositoryAccount.listOfAccounts
}