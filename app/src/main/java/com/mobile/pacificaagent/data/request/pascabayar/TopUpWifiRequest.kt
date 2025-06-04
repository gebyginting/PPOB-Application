package com.mobile.pacificaagent.data.request.pascabayar

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopUpWifiRequest(
	@SerializedName("wifiBill")
	val wifiBill: WifiBill
) : Parcelable

@Parcelize
data class WifiBill(
	@SerializedName("tagihan")
	val tagihan: Int,

	@SerializedName("pelanggan_id")
	val pelangganId: String,

	@SerializedName("paket_wifi")
	val paketWifi: String,

	@SerializedName("bulan")
	val bulan: String,

	@SerializedName("operator")
	val operator: String
) : Parcelable
