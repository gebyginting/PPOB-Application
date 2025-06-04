package com.mobile.pacificaagent.data.response.pascabayar

import com.google.gson.annotations.SerializedName

data class GetWifiProvidersResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataItem(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("operatorName")
	val operatorName: String,

	@field:SerializedName("categoryId")
	val categoryId: String
)
