package com.edwinespejo.flightnew.app.core.di.module.adapter

import com.edwinespejo.flightnew.app.adapter.controller.AppNewsController
import com.edwinespejo.flightnew.app.usecases.details.DetailNewUseCase
import com.edwinespejo.flightnew.app.usecases.news.FlightNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppNewsControllerModule {
    @Provides
    @Singleton
    fun provideAppNewsController(
        flightNewsUseCase: FlightNewsUseCase,
        detailNewUseCase: DetailNewUseCase
    ): AppNewsController = AppNewsController(flightNewsUseCase, detailNewUseCase)
}