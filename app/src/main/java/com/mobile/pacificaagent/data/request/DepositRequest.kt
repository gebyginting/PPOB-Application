package com.mobile.pacificaagent.data.request

import com.google.gson.annotations.SerializedName

data class DepositRequest(

	@field:SerializedName("metode_pembayaran")
	val metodePembayaran: String,

	@field:SerializedName("jumlah")
	val jumlah: Int
)
