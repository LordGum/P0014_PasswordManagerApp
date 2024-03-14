package com.example.passwordmanagerapp.presentation.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanagerapp.data.repositories.RepositoryWebsiteImpl
import com.example.passwordmanagerapp.domain.entities.Website
import com.example.passwordmanagerapp.domain.usecases.website.AddWebsiteUseCase
import com.example.passwordmanagerapp.domain.usecases.website.GetWebsiteInfoUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor (
    application: Application
): AndroidViewModel(application) {

    private val repositoryWebsite = RepositoryWebsiteImpl(application)

    private val getWebsiteInfoUseCase = GetWebsiteInfoUseCase(repositoryWebsite)
    private val addWebsiteUseCase = AddWebsiteUseCase(repositoryWebsite)


    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("DetailViewModel", "Exception caught by exception handler")
    }


    private val _websiteInfo = MutableStateFlow<Website?>(null)


    fun getScreenState(id: Int): DetailScreenState {
        if (id == -1) return DetailScreenState.AddState
        if (id >= 0) {
            viewModelScope.launch(exceptionHandler) {
                val r = getWebsiteInfoUseCase(id)
                //val t: Website = _websiteInfo.value as Website
                Log.d("MATAG", "website = ${r.name}")
            }
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


    fun addWebsite(website: Website) {
        viewModelScope.launch(exceptionHandler) {
            addWebsiteUseCase(website)
        }
    }

}