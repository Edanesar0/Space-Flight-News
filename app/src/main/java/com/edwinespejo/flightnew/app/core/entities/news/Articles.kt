package com.edwinespejo.flightnew.app.core.entities.news

import com.google.gson.annotations.SerializedName

data class Articles(

	@field:SerializedName("next")
	val next: String,

	@field:SerializedName("previous")
	val previous: Any,

	@field:SerializedName("count")
	val count: Int,

	@field:SerializedName("results")
	val results: List<ResultsItem>
)