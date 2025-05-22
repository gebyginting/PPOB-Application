package com.mobile.pacificaagent.data.request

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("password_confirm")
	val passwordConfirm: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("address")
	val address: String? = null
)
