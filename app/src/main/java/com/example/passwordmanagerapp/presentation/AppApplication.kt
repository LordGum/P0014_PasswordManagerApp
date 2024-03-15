package com.example.passwordmanagerapp.presentation

import android.app.Application
import com.example.passwordmanagerapp.di.DaggerApplicationComponent

class AppApplication: Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}