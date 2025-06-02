package com.mobile.pacificaagent.data.response.prabayar

import com.google.gson.annotations.SerializedName

data class TopUpPulsaResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class Data(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("amount")
	val amount: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("detail")
	val detail: Detail,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class Detail(

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("operatorId")
	val operatorId: String,

	@field:SerializedName("categoryId")
	val categoryId: String,

	@field:SerializedName("productName")
	val productName: String,

	@field:SerializedName("desc")
	val desc: String,

	@field:SerializedName("status")
	val status: Int
)
