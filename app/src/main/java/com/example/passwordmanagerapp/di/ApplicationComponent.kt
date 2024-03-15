package com.example.passwordmanagerapp.di

import android.app.Application
import com.example.passwordmanagerapp.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        ViewModelModule::class,
        DetailModule::class
    ]
)
interface ApplicationComponent {
    fun inject(activity: MainActivity)
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}