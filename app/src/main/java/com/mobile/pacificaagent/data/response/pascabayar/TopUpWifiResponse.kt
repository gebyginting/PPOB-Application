package com.mobile.pacificaagent.data.response.pascabayar

import com.google.gson.annotations.SerializedName

data class TopUpWifiResponse(

	@field:SerializedName("data")
	val data: DataWifi,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class Detail(

	@field:SerializedName("tagihan")
	val tagihan: Int,

	@field:SerializedName("pelanggan_id")
	val pelangganId: String,

	@field:SerializedName("paket_wifi")
	val paketWifi: String,

	@field:SerializedName("bulan")
	val bulan: String,

	@field:SerializedName("operator")
	val operator: String
)

data class DataWifi(

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
