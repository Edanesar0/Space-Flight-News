package com.edwinespejo.flightnew.app.adapter.repository

import com.edwinespejo.flightnew.app.adapter.interfaces.NewsRepository
import com.edwinespejo.flightnew.app.adapter.ktx.StringExtension.convertToString
import com.edwinespejo.flightnew.app.core.entities.HttpHeader
import com.edwinespejo.flightnew.app.core.entities.HttpResponse
import com.edwinespejo.flightnew.app.core.entities.news.Articles
import com.edwinespejo.flightnew.app.core.exceptions.AppException
import com.edwinespejo.flightnew.app.frameworks.connection.CommonHttpSender
import com.google.gson.Gson

class NewsRepositoryImp(
    private val commonHttpSender: CommonHttpSender
) : NewsRepository {
    override fun fetchFlightNews(
        url: String,
        onSuccess: (Articles?) -> Unit,
        onFailure: (AppException) -> Unit
    ) {
        val headers = ArrayList<HttpHeader>()
        headers.add(HttpHeader("Content-Type", "application/json"))
        val connectionRequest = commonHttpSender.getConnectionRequest(url, headers)
        handleHttpResponse(connectionRequest, onSuccess, onFailure)
    }

    private fun handleHttpResponse(
        httpResponse: HttpResponse?,
        onSuccess: (Articles?) -> Unit,
        onFailure: (AppException) -> Unit
    ) {
        httpResponse?.let {
            println("httpCode: ${it.code}  data: ${it.data} message: ${it.message}")
            when (it.code) {
                200 -> {
                    if (it.data == null || it.data!!.isEmpty()) {
                        onFailure(AppException("Empty response"))
                        return
                    }
                    val stringData = it.data?.convertToString()
                    if (stringData.isNullOrEmpty()) {
                        onFailure(AppException("Wrong response"))
                        return
                    }
                    val articles = Gson().fromJson(stringData, Articles::class.java)
                    onSuccess(articles)
                }

                else -> onFailure(AppException("${it.code}"))
            }
        } ?: run {
            onFailure(AppException("The server is unreachable, please try again later."))
        }
    }
}