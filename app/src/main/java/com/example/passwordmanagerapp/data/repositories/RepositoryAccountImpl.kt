package com.example.passwordmanagerapp.data.repositories

import com.example.passwordmanagerapp.domain.entities.Website
import com.example.passwordmanagerapp.domain.entities.WebsiteAccount
import com.example.passwordmanagerapp.domain.repositories.RepositoryAccount
import javax.inject.Inject

class RepositoryAccountImpl @Inject constructor(

): RepositoryAccount {
    override fun getListAccounts(website: Website): List<WebsiteAccount> {
        return website.accountList
    }

    override fun addAccount(website: Website, account: WebsiteAccount) {
        website.accountList.add(account)
    }

    override fun refactorAccount(website: Website, account: WebsiteAccount, ind: Int) {
        website.accountList[ind] = account
    }

}