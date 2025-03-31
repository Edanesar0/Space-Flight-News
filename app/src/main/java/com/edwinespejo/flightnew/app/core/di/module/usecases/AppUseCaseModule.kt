package com.edwinespejo.flightnew.app.core.di.module.usecases

import com.edwinespejo.flightnew.app.adapter.interfaces.ArticleRepository
import com.edwinespejo.flightnew.app.adapter.interfaces.NewsRepository
import com.edwinespejo.flightnew.app.usecases.details.DetailNewUseCase
import com.edwinespejo.flightnew.app.usecases.news.FlightNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppUseCaseModule {
    @Provides
    @Singleton
    fun provideFlightNewUseCase(newsRepository: NewsRepository):
            FlightNewsUseCase = FlightNewsUseCase(newsRepository)

    @Provides
    @Singleton
    fun provideDetailNewUseCase(articleRepository: ArticleRepository):
            DetailNewUseCase = DetailNewUseCase(articleRepository)

}
