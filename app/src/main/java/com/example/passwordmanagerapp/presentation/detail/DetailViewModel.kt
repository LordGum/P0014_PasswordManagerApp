package com.example.passwordmanagerapp.presentation.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanagerapp.data.repositories.RepositoryAccountImpl
import com.example.passwordmanagerapp.data.repositories.RepositoryWebsiteImpl
import com.example.passwordmanagerapp.domain.entities.Website
import com.example.passwordmanagerapp.domain.entities.WebsiteAccount
import com.example.passwordmanagerapp.domain.usecases.account.AddAccountUseCase
import com.example.passwordmanagerapp.domain.usecases.account.DeleteAccountUseCase
import com.example.passwordmanagerapp.domain.usecases.website.AddWebsiteUseCase
import com.example.passwordmanagerapp.domain.usecases.website.DeleteWebsiteUseCase
import com.example.passwordmanagerapp.domain.usecases.website.GetWebsiteInfoUseCase
import com.example.passwordmanagerapp.domain.usecases.website.RefactorWebsiteUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor (
    application: Application
): AndroidViewModel(application) {

    private val repositoryWebsite = RepositoryWebsiteImpl(application)
    private val repositoryAccount = RepositoryAccountImpl()

    private val getWebsiteInfoUseCase = GetWebsiteInfoUseCase(repositoryWebsite)
    private val refactorWebsiteUseCase = RefactorWebsiteUseCase(repositoryWebsite)
    private val addWebsiteUseCase = AddWebsiteUseCase(repositoryWebsite)
    private val deleteWebsiteUseCase = DeleteWebsiteUseCase(repositoryWebsite)

    private val addAccountUseCase = AddAccountUseCase(repositoryAccount)
    private val deleteAccountUseCase = DeleteAccountUseCase(repositoryAccount)

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

    fun deleteWebsite(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            deleteWebsiteUseCase(id)
        }
    }

    fun addAccount(website: Website, account: WebsiteAccount) {
        addAccountUseCase(website, account)
    }

    fun deleteAccount(website: Website, account: WebsiteAccount) {
        deleteAccountUseCase(website, account)
    }
}