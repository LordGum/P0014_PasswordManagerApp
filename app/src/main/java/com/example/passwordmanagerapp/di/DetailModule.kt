package com.example.passwordmanagerapp.di

import android.app.Application
import com.example.passwordmanagerapp.data.database.AppDatabase
import com.example.passwordmanagerapp.data.database.WebsiteListDao
import com.example.passwordmanagerapp.data.repositories.RepositoryWebsiteImpl
import com.example.passwordmanagerapp.domain.repositories.RepositoryWebsite
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DetailModule {
    @Binds
    fun bindRepository(impl: RepositoryWebsiteImpl): RepositoryWebsite

    companion object {
        @ApplicationScope
        @Provides
        fun provideWebsiteDao(
            application: Application
        ): WebsiteListDao {
            return AppDatabase.getInstance(application).websiteDao()
        }
    }
}