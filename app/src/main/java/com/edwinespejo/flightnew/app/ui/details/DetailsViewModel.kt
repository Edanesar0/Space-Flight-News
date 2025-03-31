package com.edwinespejo.flightnew.app.ui.details

import androidx.lifecycle.ViewModel
import com.edwinespejo.flightnew.app.adapter.controller.AppNewsController
import com.edwinespejo.flightnew.app.core.entities.news.ResultsItem
import com.edwinespejo.flightnew.app.core.exceptions.AppException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private var appNewsController: AppNewsController) :
    ViewModel() {
    fun getArticle(
        id: Int,
        onSuccess: (ResultsItem) -> Unit,
        onFailure: (AppException) -> Unit
    ) {
        appNewsController.getArticleById(
            id, { resultItems ->
                onSuccess(resultItems)
            }, onFailure
        )
    }


}