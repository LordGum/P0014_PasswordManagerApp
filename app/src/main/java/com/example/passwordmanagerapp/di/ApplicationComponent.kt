package com.example.passwordmanagerapp.di

import android.app.Application
import com.example.passwordmanagerapp.MainActivity
import com.example.passwordmanagerapp.presentation.ViewModelFactory
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        ViewModelModule::class,
        DetailModule::class,
        EnterModule::class
    ]
)
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory

    fun inject(activity: MainActivity)
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}