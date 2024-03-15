package com.example.passwordmanagerapp.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanagerapp.domain.usecases.website.DeleteWebsiteUseCase
import com.example.passwordmanagerapp.domain.usecases.website.GetWebsitesListUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getWebsiteListUseCase: GetWebsitesListUseCase
): ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("MainViewModel", "Exception caught by exception handler")
    }

    val screenState = getWebsiteListUseCase()
        .filter { it.isNotEmpty() }
        .map { MainScreenState.WebsiteList(websiteList = it) as MainScreenState }


}