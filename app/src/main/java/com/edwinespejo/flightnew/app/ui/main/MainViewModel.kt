package com.edwinespejo.flightnew.app.ui.main

import androidx.lifecycle.ViewModel
import com.edwinespejo.flightnew.app.adapter.controller.AppNewsController
import com.edwinespejo.flightnew.app.core.entities.news.ResultsItem
import com.edwinespejo.flightnew.app.core.exceptions.AppException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private var appNewsController: AppNewsController) :
    ViewModel() {
    fun getFlightNews(onSuccess: (List<ResultsItem>) -> Unit, onFailure: (AppException) -> Unit) {
        appNewsController.getData(
            onSuccess = { resultItems ->
                if (resultItems.isNotEmpty()) {
                    onSuccess(resultItems)
                } else {
                    onFailure(AppException("Data not found"))
                }
            },
            onFailure
        )
    }

    fun getMoreFlightNews(
        onSuccess: (List<ResultsItem>) -> Unit,
        onFailure: (AppException) -> Unit
    ) {
        appNewsController.getNextPageData(
            onSuccess = { resultItems ->
                if (resultItems.isNotEmpty()) {
                    onSuccess(resultItems)
                } else {
                    onFailure(AppException("Data not found"))
                }
            },
            onFailure
        )
    }
}