package com.example.passwordmanagerapp.domain.usecases.account

import com.example.passwordmanagerapp.domain.entities.Website
import com.example.passwordmanagerapp.domain.entities.WebsiteAccount
import com.example.passwordmanagerapp.domain.repositories.RepositoryAccount
import javax.inject.Inject

class AddAccountUseCase @Inject constructor(
    private val repositoryAccount: RepositoryAccount
) {
    operator fun invoke(website: Website, account: WebsiteAccount)
    = repositoryAccount.addAccount(website, account)
}