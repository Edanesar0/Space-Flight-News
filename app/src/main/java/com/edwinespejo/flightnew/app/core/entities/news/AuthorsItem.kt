package com.edwinespejo.flightnew.app.core.entities.news

import com.google.gson.annotations.SerializedName

data class AuthorsItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("socials")
	val socials: Socials
)