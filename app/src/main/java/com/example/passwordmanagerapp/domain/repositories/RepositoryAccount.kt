package com.example.passwordmanagerapp.domain.repositories

import com.example.passwordmanagerapp.domain.entities.WebsiteAccount
import kotlinx.coroutines.flow.Flow

interface RepositoryAccount {

    val listOfAccounts: Flow<List<WebsiteAccount>>

    suspend fun addAccount(account: WebsiteAccount)

    suspend fun refactorAccount(account: WebsiteAccount)
}