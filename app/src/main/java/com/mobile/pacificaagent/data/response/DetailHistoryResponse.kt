package com.mobile.pacificaagent.data.response

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class HistoryDetailResponse(
    @SerializedName("data")
    val data: HistoryDetailData,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)

data class HistoryDetailData(
    val id: String,
    val userId: String,
    val type: String,
    val amount: Int,
    val status: String,
    val detail: JsonElement, // bisa di-parse manual sesuai `type`
    val createdAt: String,
    val updatedAt: String
)

// TOP-UP TOKEN
data class TokenListrikDetail(
    val hasil: HasilToken,
    val input: InputToken,
    val ppjInfo: PPJInfo,
    val breakdown: Breakdown,
    val wilayahInfo: WilayahInfo
)

data class HasilToken(val tokenNumber: String, val kwhDiperoleh: Double, val nominalEfektif: Int)
data class InputToken(val wilayah: String, val customerId: String, val tarifPerKWh: Int, val nominalBayar: Int, val golonganTarif: String)
data class PPJInfo(val rate: Double, val detail: PPJDetail, val faktor: String, val kategori: String, val keterangan: String, val persentase: String)
data class PPJDetail(val range: String, val baseRate: Double, val finalRate: Double, val wilayahMultiplier: Double)
data class Breakdown(val ppj: Int, val ppn: Int, val total: Int, val biayaAdmin: Int, val tarifDasar: Int, val totalPajak: Int, val biayaMeterai: Int, val totalBiayaTambahan: Int)
data class WilayahInfo(val nama: String, val pulau: String, val wilayah: String, val kategori: String, val kodeArea: String, val isDefault: Boolean)

// TOP-UP E-WALLET
data class EWalletDetail(
    val harga: Int,
    val nominal: Int,
    val nameApps: String
)

// TOP-UP PULSA
data class PulsaDataDetail(
    val id: String,
    val desc: String,
    val price: Int,
    val status: Int,
    val categoryId: String,
    val operatorId: String,
    val productName: String
)

// TOP-UP DEPOSIT
data class DepositDetail(
    val VA: String,
    val bank: String,
    val detail: DepositInnerDetail
)

data class DepositInnerDetail(
    val date: Long,
    val amount: Int,
    val merchant_ref: String,
    val code_pembayaran: String
)
