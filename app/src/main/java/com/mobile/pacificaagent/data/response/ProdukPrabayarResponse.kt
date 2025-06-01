package com.mobile.pacificaagent.data.response

import com.google.gson.annotations.SerializedName

data class ProdukPrabayarResponse(

	@field:SerializedName("data")
	val data: List<DataProdukPrabayar>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DetailProdukPrabayarResponse(

	@field:SerializedName("data")
	val data: DataProdukPrabayar,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataProdukPrabayar(

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
