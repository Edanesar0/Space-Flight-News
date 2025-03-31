package com.edwinespejo.flightnew.app.adapter.repository

import com.edwinespejo.flightnew.app.adapter.interfaces.ArticleRepository
import com.edwinespejo.flightnew.app.adapter.ktx.StringExtension.convertToString
import com.edwinespejo.flightnew.app.core.entities.HttpHeader
import com.edwinespejo.flightnew.app.core.entities.HttpResponse
import com.edwinespejo.flightnew.app.core.entities.news.ResultsItem
import com.edwinespejo.flightnew.app.core.exceptions.AppException
import com.edwinespejo.flightnew.app.frameworks.connection.CommonHttpSender
import com.google.gson.Gson

class ArticleRepositoryImp(
    private val commonHttpSender: CommonHttpSender
) : ArticleRepository {

    override fun fetchArticle(
        url: String,
        onSuccess: (ResultsItem) -> Unit,
        onFailure: (AppException) -> Unit
    ) {
        val headers = ArrayList<HttpHeader>()
        headers.add(HttpHeader("Content-Type", "application/json"))
        val connectionRequest = commonHttpSender.getConnectionRequest(url, headers)
        handleHttpResponse(connectionRequest, onSuccess, onFailure)
    }

    private fun handleHttpResponse(
        httpResponse: HttpResponse?,
        onSuccess: (ResultsItem) -> Unit,
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
                    val articles = Gson().fromJson(stringData, ResultsItem::class.java)
                    onSuccess(articles)
                }

                else -> onFailure(AppException("${it.code}"))
            }
        } ?: run {
            onFailure(AppException("The server is unreachable, please try again later."))
        }
    }
}