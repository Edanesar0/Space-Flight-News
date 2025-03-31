package com.edwinespejo.flightnew.app.usecases.news

import com.edwinespejo.flightnew.app.adapter.interfaces.NewsRepository
import com.edwinespejo.flightnew.app.core.entities.news.Articles
import com.edwinespejo.flightnew.app.core.exceptions.AppException

class FlightNewsUseCase(
    private val newsRepository: NewsRepository
) {
    fun execute(
        onSuccess: (Articles?) -> Unit,
        onFailure: (AppException) -> Unit
    ) {
        newsRepository.fetchFlightNews(URL_SERVER + URL_NEWS, onSuccess, onFailure)
    }

    fun execute(
        page: String,
        onSuccess: (Articles?) -> Unit,
        onFailure: (AppException) -> Unit
    ) {
        newsRepository.fetchFlightNews(page, onSuccess, onFailure)
    }

    companion object {
        private const val URL_SERVER = "https://api.spaceflightnewsapi.net/v4/"
        private const val URL_NEWS = "articles/?format=json"
    }
}