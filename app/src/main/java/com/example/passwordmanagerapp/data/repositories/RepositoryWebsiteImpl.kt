package com.example.passwordmanagerapp.data.repositories

import com.example.passwordmanagerapp.data.database.WebsiteListDao
import com.example.passwordmanagerapp.data.mappers.Mapper
import com.example.passwordmanagerapp.domain.entities.Website
import com.example.passwordmanagerapp.domain.repositories.RepositoryWebsite
import com.example.passwordmanagerapp.security.CryptoManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryWebsiteImpl @Inject constructor(
    private val mapper: Mapper,
    private val websiteDao: WebsiteListDao,
    private val cryptoManager: CryptoManager
) : RepositoryWebsite {



    override val websitesList: Flow<List<Website>> = websiteDao.getWebsitesList().map {
        it.map { dbModel ->
            mapper.dbModelToEntity(dbModel)
        }
    }

    override suspend fun addWebsite(website: Website) {
        website.cipheredLogin =
            cryptoManager.encrypt(website.cipheredLogin) ?: throw RuntimeException("login is null")
        website.cipheredPassword =
            cryptoManager.encrypt(website.cipheredPassword) ?: throw RuntimeException("password is null")
        websiteDao.addWebsite(mapper.entityToDbModel(website))
    }

    override suspend fun refactorWebsite(website: Website) {
        websiteDao.addWebsite(mapper.entityToDbModel(website))
    }

    override suspend fun deleteWebsite(id: Int) {
        websiteDao.deleteWebsite(id)
    }

    override suspend fun getWebsiteInfo(id: Int): Website {
        val dbModel = websiteDao.getWebsiteInfo(id)
        dbModel.cipheredLogin =
            cryptoManager.decrypt(dbModel.cipheredLogin) ?: throw RuntimeException("login is null")
        dbModel.cipheredPassword =
            cryptoManager.decrypt(dbModel.cipheredPassword) ?: throw RuntimeException("password is null")
        return mapper.dbModelToEntity(dbModel)
    }
}