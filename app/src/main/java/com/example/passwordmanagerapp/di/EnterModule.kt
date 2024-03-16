package com.example.passwordmanagerapp.di

import com.example.passwordmanagerapp.data.repositories.RepositoryEnterImpl
import com.example.passwordmanagerapp.domain.repositories.RepositoryEnter
import dagger.Binds
import dagger.Module

@Module
interface EnterModule {
    @Binds
    fun bindRepository(impl: RepositoryEnterImpl): RepositoryEnter
}