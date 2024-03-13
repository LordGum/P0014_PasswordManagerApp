package com.example.passwordmanagerapp.presentation.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanagerapp.data.repositories.RepositoryWebsiteImpl
import com.example.passwordmanagerapp.domain.entities.Website
import com.example.passwordmanagerapp.domain.usecases.website.AddWebsiteUseCase
import com.example.passwordmanagerapp.domain.usecases.website.DeleteWebsiteUseCase
import com.example.passwordmanagerapp.domain.usecases.website.GetWebsiteInfoUseCase
import com.example.passwordmanagerapp.domain.usecases.website.RefactorWebsiteUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailViewModel (
    application: Application
): ViewModel() {
    private val repository = RepositoryWebsiteImpl(application)

    private val getWebsiteInfoUseCase = GetWebsiteInfoUseCase(repository)
    private val refactorWebsiteUseCase = RefactorWebsiteUseCase(repository)
    private val addWebsiteUseCase = AddWebsiteUseCase(repository)
    private val deleteWebsiteUseCase = DeleteWebsiteUseCase(repository)

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("DetailViewModel", "Exception caught by exception handler")
    }


    private val _websiteInfo = MutableStateFlow<Website?>(null)

    fun getScreenState(id: Int): DetailScreenState {
        if (id == -1) return DetailScreenState.AddState
        if (id >= 0) {
            getWebsiteInfo(id)
            return DetailScreenState.RefactorState(
                _websiteInfo.value ?: throw RuntimeException("websiteInfo is null")
            )
        }
        else return DetailScreenState.Initial
    }

    private fun getWebsiteInfo(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            _websiteInfo.value = getWebsiteInfoUseCase(id)
        }
    }

    fun refactorWebsite(website: Website) {
        viewModelScope.launch(exceptionHandler) {
            refactorWebsiteUseCase(website)
        }
    }

    fun addWebsite(website: Website) {
        viewModelScope.launch(exceptionHandler) {
            addWebsiteUseCase(website)
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            deleteWebsiteUseCase(id)
        }
    }
}