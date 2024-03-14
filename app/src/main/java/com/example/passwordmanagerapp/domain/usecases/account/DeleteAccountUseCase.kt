package com.example.passwordmanagerapp.domain.usecases.account

import com.example.passwordmanagerapp.domain.entities.Website
import com.example.passwordmanagerapp.domain.entities.WebsiteAccount
import com.example.passwordmanagerapp.domain.repositories.RepositoryAccount

class DeleteAccountUseCase(
    private val repository: RepositoryAccount
) {
    operator fun invoke(website: Website, account: WebsiteAccount) = repository.deleteAccount(website, account)
}