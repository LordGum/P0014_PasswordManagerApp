package com.example.passwordmanagerapp.domain.usecases

import com.example.passwordmanagerapp.domain.entities.WebsiteAccount
import com.example.passwordmanagerapp.domain.repositories.RepositoryAccount

class AddAccountUseCase(
    private val repositoryAccount: RepositoryAccount
) {
    suspend operator fun invoke(account: WebsiteAccount) = repositoryAccount.addAccount(account)
}