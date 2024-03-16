package com.example.passwordmanagerapp.presentation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.passwordmanagerapp.di.ApplicationComponent
import com.example.passwordmanagerapp.di.DaggerApplicationComponent

class AppApplication: Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as AppApplication).component
}