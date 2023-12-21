package com.bangkit.minatku.data.response

import com.google.gson.annotations.SerializedName

data class UserUpdate(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class UpdatePP(
	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("file_url")
	val file_url: String? = null
)
