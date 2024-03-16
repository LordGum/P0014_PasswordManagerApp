package com.example.passwordmanagerapp.di

import androidx.lifecycle.ViewModel
import com.example.passwordmanagerapp.presentation.detail.DetailViewModel
import com.example.passwordmanagerapp.presentation.enter.EnterViewModel
import com.example.passwordmanagerapp.presentation.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    fun bindDetailViewModel(viewModel: DetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EnterViewModel::class)
    fun bindEnterViewModel(viewModel: EnterViewModel): ViewModel
}