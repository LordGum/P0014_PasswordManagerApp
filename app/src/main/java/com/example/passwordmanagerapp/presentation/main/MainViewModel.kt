package com.example.passwordmanagerapp.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanagerapp.data.internal_storage.StorageManager
import com.example.passwordmanagerapp.domain.usecases.website.GetWebsitesListUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getWebsiteListUseCase: GetWebsitesListUseCase,
    private val storageManager: StorageManager
): ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("MainViewModel", "Exception caught by exception handler")
    }

    val screenState = getWebsiteListUseCase()
        .filter { it.isNotEmpty() }
        .map { MainScreenState.WebsiteList(websiteList = it) as MainScreenState }

    fun getBitMap(fileName: String) = viewModelScope.async(exceptionHandler) {
        storageManager.loadPhoto(fileName)
    }


}