package com.mobile.pacificaagent.data.response.pascabayar

import com.google.gson.annotations.SerializedName

data class TagihanWifiResponse(

	@field:SerializedName("data")
	val data: DataTagihan,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataTagihan(

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
