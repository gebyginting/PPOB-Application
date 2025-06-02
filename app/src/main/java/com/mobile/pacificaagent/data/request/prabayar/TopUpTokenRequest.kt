package com.mobile.pacificaagent.data.request.prabayar

import com.google.gson.annotations.SerializedName

data class TopUpTokenRequest(

	@field:SerializedName("amount")
	val amount: Int,

	@field:SerializedName("nomorMeter")
	val nomorMeter: String
)
