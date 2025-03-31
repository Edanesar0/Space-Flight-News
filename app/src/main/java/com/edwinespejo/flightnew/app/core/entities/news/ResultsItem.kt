package com.edwinespejo.flightnew.app.core.entities.news

import com.edwinespejo.flightnew.app.ui.adapter.ListItemViewModel
import com.google.gson.annotations.SerializedName

data class ResultsItem(

	@field:SerializedName("summary")
	val summary: String,

	@field:SerializedName("news_site")
	val newsSite: String,

	@field:SerializedName("featured")
	val featured: Boolean,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("published_at")
	val publishedAt: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("launches")
	val launches: List<Any>,

	@field:SerializedName("events")
	val events: List<Any>,

	@field:SerializedName("authors")
	val authors: List<AuthorsItem>
): ListItemViewModel()