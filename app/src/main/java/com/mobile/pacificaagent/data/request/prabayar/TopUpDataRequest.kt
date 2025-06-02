package com.mobile.pacificaagent.data.request.prabayar

import com.google.gson.annotations.SerializedName

data class TopUpDataRequest(

	@field:SerializedName("number")
	val number: String,

	@field:SerializedName("paket")
	val paket: Paket
)

data class Paket(

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("operatorId")
	val operatorId: String,

	@field:SerializedName("productName")
	val productName: String,

	@field:SerializedName("categoryId")
	val categoryId: String,

	@field:SerializedName("desc")
	val desc: String,

	@field:SerializedName("status")
	val status: Int
)
