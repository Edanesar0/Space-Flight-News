package com.edwinespejo.flightnew.app.core.entities.news

import com.google.gson.annotations.SerializedName

data class Socials(

	@field:SerializedName("youtube")
	val youtube: String,

	@field:SerializedName("x")
	val x: String,

	@field:SerializedName("mastodon")
	val mastodon: String,

	@field:SerializedName("bluesky")
	val bluesky: String,

	@field:SerializedName("instagram")
	val instagram: String,

	@field:SerializedName("linkedin")
	val linkedin: String
)