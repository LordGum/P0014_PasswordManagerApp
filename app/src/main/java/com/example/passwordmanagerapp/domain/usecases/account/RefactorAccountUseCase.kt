package com.example.passwordmanagerapp.domain.usecases.account

import com.example.passwordmanagerapp.domain.entities.Website
import com.example.passwordmanagerapp.domain.entities.WebsiteAccount
import com.example.passwordmanagerapp.domain.repositories.RepositoryAccount
import javax.inject.Inject

class RefactorAccountUseCase @Inject constructor(
    private val repositoryAccount: RepositoryAccount
) {
    operator fun invoke(website: Website, account: WebsiteAccount, ind: Int)
    = repositoryAccount.refactorAccount(website, account, ind)
}