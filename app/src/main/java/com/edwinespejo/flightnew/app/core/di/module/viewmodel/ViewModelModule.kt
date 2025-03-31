package com.edwinespejo.flightnew.app.core.di.module.viewmodel

import com.edwinespejo.flightnew.app.adapter.controller.AppNewsController
import com.edwinespejo.flightnew.app.ui.main.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideMainViewModel(appNewsController: AppNewsController) =
        MainViewModel(appNewsController)
}
