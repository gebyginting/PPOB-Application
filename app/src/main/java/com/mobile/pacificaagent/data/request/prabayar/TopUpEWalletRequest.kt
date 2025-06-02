package com.mobile.pacificaagent.data.request.prabayar

import com.google.gson.annotations.SerializedName

data class TopUpEWalletRequest(

	@field:SerializedName("number")
	val number: String,

	@field:SerializedName("ewallet")
	val ewallet: Ewallet
)

data class Ewallet(

	@field:SerializedName("nameApps")
	val nameApps: String,

	@field:SerializedName("nominal")
	val nominal: Int,

	@field:SerializedName("harga")
	val harga: Int
)
