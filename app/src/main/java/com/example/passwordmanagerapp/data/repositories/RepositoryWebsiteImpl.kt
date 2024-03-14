package com.example.passwordmanagerapp.data.repositories

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.activity.ComponentActivity
import coil.imageLoader
import coil.request.ImageRequest
import com.example.passwordmanagerapp.data.database.AppDatabase
import com.example.passwordmanagerapp.data.mappers.Mapper
import com.example.passwordmanagerapp.domain.entities.Website
import com.example.passwordmanagerapp.domain.repositories.RepositoryWebsite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException

class RepositoryWebsiteImpl(
    private val application: Context
) : RepositoryWebsite {

    private val websiteDao = AppDatabase.getInstance(application).recordDao()
    private val mapper = Mapper()


    override val websitesList: Flow<List<Website>> = websiteDao.getWebsitesList().map {
        it.map { dbModel ->
            mapper.dbModelToEntity(dbModel)
        }
    }

    override suspend fun addWebsite(website: Website) {
        // TODO (ПРОБЛЕМА С СОХРАНЕНИЕМ)
        //val bmp = urlToBitmap(website.address)
        //saveImage(website.iconFileName, bmp)
        websiteDao.addWebsite(mapper.entityToDbModel(website))
    }

    override suspend fun refactorWebsite(website: Website) {
        val bmp = urlToBitmap(website.address)
        saveImage(website.iconFileName, bmp)
        websiteDao.addWebsite(mapper.entityToDbModel(website))
    }

    override suspend fun deleteWebsite(idWebsite: Int) {
        websiteDao.deleteWebsite(idWebsite)
    }

    override suspend fun getWebsiteInfo(idWebsite: Int): Website {
        Log.d("MATAG", "id in rep = $idWebsite")
        val e = websiteDao.getWebsiteInfo(idWebsite)
        Log.d("MATAG", "info = ${e.name}")
        return mapper.dbModelToEntity(websiteDao.getWebsiteInfo(idWebsite))
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