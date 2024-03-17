package com.example.passwordmanagerapp.data.internal_storage


import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import coil.imageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import javax.inject.Inject

class StorageManager @Inject constructor(
    private val application: Application,
) {
    suspend fun urlToBitmap(url: String): Bitmap {
        val newUrl = "https://www.google.com/s2/favicons?sz=64&domain_url=$url"
        val imageLoader = application.imageLoader

        val request = ImageRequest.Builder(application)
            .data(newUrl)
            .build()
        val drawable = imageLoader.execute(request).drawable ?: throw RuntimeException("drawable is null")
        return (drawable as BitmapDrawable).bitmap
    }

    fun savePhoto(filename: String, bmp: Bitmap): Boolean {
        return try {
            application.openFileOutput("$filename.jpg", MODE_PRIVATE).use { stream ->
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

    suspend fun loadPhoto(filename: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            val file = File(application.filesDir, "$filename.jpg")
            if(file.canRead() && file.isFile) {
                val bytes = file.readBytes()
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            } else {
                null
            }
        }
    }
}