package com.mobile.pacificaagent.data.response

import com.google.gson.annotations.SerializedName

data class GetBalanceResponse(

	@field:SerializedName("data")
	val data: BalanceData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class BalanceData(

	@field:SerializedName("balance")
	val balance: Int
)
