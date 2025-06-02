package com.mobile.pacificaagent.data.response.prabayar

import com.google.gson.annotations.SerializedName

data class TopUpEWalletResponse(

	@field:SerializedName("data")
	val data: DataEWallet,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DetailEWallet(

	@field:SerializedName("nameApps")
	val nameApps: String,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("nominal")
	val nominal: Int
)

data class DataEWallet(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("amount")
	val amount: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("detail")
	val detail: DetailEWallet,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
