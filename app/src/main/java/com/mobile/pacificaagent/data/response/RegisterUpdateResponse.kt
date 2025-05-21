package com.mobile.pacificaagent.data.response

import com.google.gson.annotations.SerializedName

data class RegisterUpdateResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
