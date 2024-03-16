package com.example.passwordmanagerapp.data.repositories

import android.app.Application
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.activity.ComponentActivity
import coil.imageLoader
import coil.request.ImageRequest
import com.example.passwordmanagerapp.data.database.WebsiteListDao
import com.example.passwordmanagerapp.data.mappers.Mapper
import com.example.passwordmanagerapp.domain.entities.Website
import com.example.passwordmanagerapp.domain.repositories.RepositoryWebsite
import com.example.passwordmanagerapp.security.CryptoManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class RepositoryWebsiteImpl @Inject constructor(
    private val application: Application,
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
        // TODO (ПРОБЛЕМА С СОХРАНЕНИЕМ)
        //val bmp = urlToBitmap(website.address)
        //saveImage(website.iconFileName, bmp)
        website.cipheredLogin =
            cryptoManager.encrypt(website.cipheredLogin) ?: throw RuntimeException("login is null")
        website.cipheredPassword =
            cryptoManager.encrypt(website.cipheredPassword) ?: throw RuntimeException("password is null")
        websiteDao.addWebsite(mapper.entityToDbModel(website))
    }

    override suspend fun refactorWebsite(website: Website) {
        val bmp = urlToBitmap(website.address)
        saveImage(website.iconFileName, bmp)
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

    private suspend fun urlToBitmap(url: String): Bitmap {
        val imageLoader = application.imageLoader

        val request = ImageRequest.Builder(application)
            .data(url)
            .build()
        val drawable = imageLoader.execute(request).drawable ?: throw RuntimeException("drawable is null")
        return (drawable as BitmapDrawable).bitmap
    }

    private fun saveImage(filename: String, bmp: Bitmap): Boolean {
        return try {
            application.openFileOutput("$filename.jpg", ComponentActivity.MODE_PRIVATE).use { stream ->
                if(!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
            }
            true
        } catch(e: IOException) {
            e.printStackTrace()
            false
        }
    }
}