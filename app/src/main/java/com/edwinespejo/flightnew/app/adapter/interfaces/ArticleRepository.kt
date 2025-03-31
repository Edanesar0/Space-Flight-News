package com.edwinespejo.flightnew.app.adapter.interfaces

import com.edwinespejo.flightnew.app.core.entities.news.ResultsItem
import com.edwinespejo.flightnew.app.core.exceptions.AppException

interface ArticleRepository {
    fun fetchArticle(
        url: String,
        onSuccess: (ResultsItem) -> Unit,
        onFailure: (AppException) -> Unit
    )
}