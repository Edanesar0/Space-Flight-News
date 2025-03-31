package com.edwinespejo.flightnew.app.ui.adapter

import com.edwinespejo.flightnew.app.core.entities.news.ResultsItem

abstract class ListItemViewModel {
    var adapterPosition: Int = -1
    var onListItemViewClickListener: GenericAdapter.OnListItemViewClickListener? = null
    var isVisible: Boolean = false
}