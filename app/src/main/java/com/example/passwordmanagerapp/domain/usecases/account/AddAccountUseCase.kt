package com.example.passwordmanagerapp.domain.usecases.account

import com.example.passwordmanagerapp.domain.entities.WebsiteAccount
import com.example.passwordmanagerapp.domain.repositories.RepositoryAccount
import javax.inject.Inject

class AddAccountUseCase @Inject constructor(
    private val repositoryAccount: RepositoryAccount
) {
    suspend operator fun invoke(account: WebsiteAccount) = repositoryAccount.addAccount(account)
}