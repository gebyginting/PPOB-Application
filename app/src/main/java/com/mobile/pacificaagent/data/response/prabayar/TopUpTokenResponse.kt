package com.mobile.pacificaagent.data.response.prabayar

import com.google.gson.annotations.SerializedName

data class TopUpTokenResponse(

	@field:SerializedName("data")
	val data: DataToken,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataToken(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("amount")
	val amount: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("detail")
	val detail: DetailToken,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class PpjInfo(

	@field:SerializedName("faktor")
	val faktor: String,

	@field:SerializedName("keterangan")
	val keterangan: String,

	@field:SerializedName("rate")
	val rate: Any,

	@field:SerializedName("kategori")
	val kategori: String,

	@field:SerializedName("detail")
	val detail: DetailToken,

	@field:SerializedName("persentase")
	val persentase: String
)

data class Input(

	@field:SerializedName("golonganTarif")
	val golonganTarif: String,

	@field:SerializedName("nominalBayar")
	val nominalBayar: Int,

	@field:SerializedName("customerId")
	val customerId: String,

	@field:SerializedName("wilayah")
	val wilayah: String,

	@field:SerializedName("tarifPerKWh")
	val tarifPerKWh: Int
)

data class Breakdown(

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("totalBiayaTambahan")
	val totalBiayaTambahan: Int,

	@field:SerializedName("biayaAdmin")
	val biayaAdmin: Int,

	@field:SerializedName("tarifDasar")
	val tarifDasar: Int,

	@field:SerializedName("totalPajak")
	val totalPajak: Int,

	@field:SerializedName("biayaMeterai")
	val biayaMeterai: Int,

	@field:SerializedName("ppj")
	val ppj: Int,

	@field:SerializedName("ppn")
	val ppn: Int
)

data class WilayahInfo(

	@field:SerializedName("isDefault")
	val isDefault: Boolean,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("kodeArea")
	val kodeArea: String,

	@field:SerializedName("pulau")
	val pulau: String,

	@field:SerializedName("kategori")
	val kategori: String,

	@field:SerializedName("wilayah")
	val wilayah: String
)

data class Hasil(

	@field:SerializedName("kwhDiperoleh")
	val kwhDiperoleh: Any,

	@field:SerializedName("tokenNumber")
	val tokenNumber: String,

	@field:SerializedName("nominalEfektif")
	val nominalEfektif: Int
)

data class DetailToken(

	@field:SerializedName("input")
	val input: Input,

	@field:SerializedName("wilayahInfo")
	val wilayahInfo: WilayahInfo,

	@field:SerializedName("breakdown")
	val breakdown: Breakdown,

	@field:SerializedName("ppjInfo")
	val ppjInfo: PpjInfo,

	@field:SerializedName("hasil")
	val hasil: Hasil,

	@field:SerializedName("baseRate")
	val baseRate: Any,

	@field:SerializedName("range")
	val range: String,

	@field:SerializedName("finalRate")
	val finalRate: Any,

	@field:SerializedName("wilayahMultiplier")
	val wilayahMultiplier: Any
)
