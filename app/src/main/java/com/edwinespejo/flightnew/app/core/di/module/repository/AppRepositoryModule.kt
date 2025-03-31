package com.edwinespejo.flightnew.app.core.di.module.repository

import com.edwinespejo.flightnew.app.adapter.interfaces.ArticleRepository
import com.edwinespejo.flightnew.app.adapter.interfaces.NewsRepository
import com.edwinespejo.flightnew.app.adapter.repository.ArticleRepositoryImp
import com.edwinespejo.flightnew.app.adapter.repository.NewsRepositoryImp
import com.edwinespejo.flightnew.app.frameworks.connection.CommonHttpSender
import com.edwinespejo.flightnew.app.frameworks.connection.GenericConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppRepositoryModule {
    @Provides
    @Singleton
    fun provideNewsRepository(
    ): NewsRepository {
        val genericConnection = GenericConnection()
        val commonHttpSender = CommonHttpSender(genericConnection)
        return NewsRepositoryImp(commonHttpSender)
    }

    @Provides
    @Singleton
    fun provideArticleRepository(
    ): ArticleRepository {
        val genericConnection = GenericConnection()
        val commonHttpSender = CommonHttpSender(genericConnection)
        return ArticleRepositoryImp(commonHttpSender)
    }

}