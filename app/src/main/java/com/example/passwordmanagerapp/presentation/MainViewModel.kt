package com.example.passwordmanagerapp.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanagerapp.data.repositories.RepositoryWebsiteImpl
import com.example.passwordmanagerapp.domain.entities.Website
import com.example.passwordmanagerapp.domain.usecases.website.AddWebsiteUseCase
import com.example.passwordmanagerapp.domain.usecases.website.DeleteWebsiteUseCase
import com.example.passwordmanagerapp.domain.usecases.website.GetWebsitesListUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    application: Application
): AndroidViewModel(application) {

    private val repository = RepositoryWebsiteImpl(application)

    private val addWebsiteUseCase =  AddWebsiteUseCase(repository)
    private val deleteWebsiteUseCase =  DeleteWebsiteUseCase(repository)
    private val getWebsiteListUseCase =  GetWebsitesListUseCase(repository)

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("MainViewModel", "Exception caught by exception handler")
    }

    val screenState = getWebsiteListUseCase()
        .filter { it.isNotEmpty() }
        .map { MainScreenState.WebsiteList(websiteList = it) as MainScreenState }

    fun addWebsite(website: Website) {
        viewModelScope.launch(exceptionHandler) {
            addWebsiteUseCase(website)
        }
    }

    fun deleteWebsite(idWebsite: Int) {
        viewModelScope.launch(exceptionHandler) {
            deleteWebsiteUseCase(idWebsite)
        }
    }

}