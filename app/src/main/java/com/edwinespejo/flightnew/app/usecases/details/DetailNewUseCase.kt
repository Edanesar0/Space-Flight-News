package com.edwinespejo.flightnew.app.usecases.details

import com.edwinespejo.flightnew.app.adapter.interfaces.ArticleRepository
import com.edwinespejo.flightnew.app.core.entities.news.ResultsItem
import com.edwinespejo.flightnew.app.core.exceptions.AppException

class DetailNewUseCase(
    private val articleRepository: ArticleRepository
) {
    fun execute(
        id: Int,
        onSuccess: (ResultsItem) -> Unit,
        onFailure: (AppException) -> Unit
    ) {
        articleRepository.fetchArticle(
            URL_SERVER + URL_NEWS + id + URL_FORMAT,
            onSuccess,
            onFailure
        )
    }


    companion object {
        private const val URL_SERVER = "https://api.spaceflightnewsapi.net/v4/"
        private const val URL_NEWS = "articles/"
        private const val URL_FORMAT = "/?format=json"
    }
}