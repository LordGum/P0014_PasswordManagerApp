package com.example.passwordmanagerapp.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanagerapp.data.internal_storage.StorageManager
import com.example.passwordmanagerapp.domain.entities.Website
import com.example.passwordmanagerapp.domain.usecases.website.AddWebsiteUseCase
import com.example.passwordmanagerapp.domain.usecases.website.DeleteWebsiteUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor (
    private val addWebsiteUseCase: AddWebsiteUseCase,
    private val deleteWebsiteUseCase: DeleteWebsiteUseCase,
    private val storageManager: StorageManager
): ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("DetailViewModel", "Exception caught by exception handler")
    }

    fun addWebsite(website: Website) {
        viewModelScope.launch(exceptionHandler) {
            addWebsiteUseCase(website)
            saveImage(website.address, website.iconFileName)
        }
    }

    private suspend fun saveImage(url: String, fileName: String) {
        val bitmap = storageManager.urlToBitmap(url)
        storageManager.savePhoto(fileName, bitmap)
    }

    fun parseWebsite(website: Website): Error {
        if(website.name.trim().isBlank()) return Error.NAME
        if(website.address.trim().isBlank()) return Error.ADDRESS
        if(website.cipheredLogin.trim().isBlank()) return Error.LOGIN
        if(website.cipheredPassword.trim().isBlank()) return Error.PASSWORD

        return Error.OKAY
    }

    fun deleteWebsite(idWebsite: Int) {
        viewModelScope.launch(exceptionHandler) {
            deleteWebsiteUseCase(idWebsite)
        }
    }
}