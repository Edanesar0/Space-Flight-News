package com.edwinespejo.flightnew.app.adapter.controller

import com.edwinespejo.flightnew.app.core.entities.news.ResultsItem
import com.edwinespejo.flightnew.app.core.exceptions.AppException
import com.edwinespejo.flightnew.app.usecases.details.DetailNewUseCase
import com.edwinespejo.flightnew.app.usecases.news.FlightNewsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppNewsController @Inject constructor(
    private var flightNewsUseCase: FlightNewsUseCase,
    private var detailNewUseCase: DetailNewUseCase,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val scope = CoroutineScope(ioDispatcher)
    private var nextPage: String? = null

    fun getData(
        onSuccess: (List<ResultsItem>) -> Unit,
        onFailure: (AppException) -> Unit
    ) {
        scope.launch {
            flightNewsUseCase.execute({ articles ->
                launch(Dispatchers.Main) {
                    nextPage = articles?.next
                    onSuccess(articles!!.results)
                }
            }, { launch(Dispatchers.Main) { onFailure(it) } })
        }
    }

    fun getNextPageData(
        onSuccess: (List<ResultsItem>) -> Unit,
        onFailure: (AppException) -> Unit
    ) {
        scope.launch {
            if (nextPage != null)
                flightNewsUseCase.execute(
                    nextPage!!,
                    { articles ->
                        launch(Dispatchers.Main) {
                            nextPage = articles?.next
                            onSuccess(articles!!.results)
                        }
                    }, { launch(Dispatchers.Main) { onFailure(it) } })
        }
    }

    fun getArticleById(
        id: Int,
        onSuccess: (ResultsItem) -> Unit,
        onFailure: (AppException) -> Unit
    ) {
        scope.launch {
            if (nextPage != null)
                detailNewUseCase.execute(
                    id,
                    { articles ->
                        launch(Dispatchers.Main) { onSuccess(articles) }
                    }, { launch(Dispatchers.Main) { onFailure(it) } })
        }
    }


}