package com.edwinespejo.flightnew.app.adapter.interfaces

import com.edwinespejo.flightnew.app.core.entities.news.Articles
import com.edwinespejo.flightnew.app.core.exceptions.AppException

interface NewsRepository {
    fun fetchFlightNews(
        url:String,
        onSuccess: (Articles?) -> Unit,
        onFailure: (AppException) -> Unit
    )
}