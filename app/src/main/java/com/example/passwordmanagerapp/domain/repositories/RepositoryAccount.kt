package com.example.passwordmanagerapp.domain.repositories

import com.example.passwordmanagerapp.domain.entities.Website
import com.example.passwordmanagerapp.domain.entities.WebsiteAccount

interface RepositoryAccount {

     fun getListAccounts(website: Website): List<WebsiteAccount>

     fun addAccount(website: Website, account: WebsiteAccount)

     fun refactorAccount(website: Website, account: WebsiteAccount, ind: Int)
}