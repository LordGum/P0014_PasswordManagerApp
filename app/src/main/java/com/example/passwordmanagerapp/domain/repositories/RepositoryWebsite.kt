package com.example.passwordmanagerapp.domain.repositories

import com.example.passwordmanagerapp.domain.entities.Website
import kotlinx.coroutines.flow.Flow

interface RepositoryWebsite {

    val websitesList: Flow<List<Website>>

    suspend fun addWebsite(website: Website)

    suspend fun refactorWebsite(website: Website)

    suspend fun deleteWebsite(idWebsite: Int)

    suspend fun getWebsiteInfo(idWebsite: Int): Website

}