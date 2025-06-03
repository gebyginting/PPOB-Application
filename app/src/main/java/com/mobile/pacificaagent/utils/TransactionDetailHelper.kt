package com.mobile.pacificaagent.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.mobile.pacificaagent.data.response.DepositDetail
import com.mobile.pacificaagent.data.response.EWalletDetail
import com.mobile.pacificaagent.data.response.HistoryDetailData
import com.mobile.pacificaagent.data.response.PulsaDataDetail
import com.mobile.pacificaagent.data.response.TokenListrikDetail

sealed class TransactionDetail {
    data class Token(val data: HistoryDetailData, val detail: TokenListrikDetail) : TransactionDetail()
    data class EWallet(val data: HistoryDetailData, val detail: EWalletDetail) : TransactionDetail()
    data class PulsaData(val data: HistoryDetailData, val detail: PulsaDataDetail) : TransactionDetail()
    data class Deposit(val data: HistoryDetailData, val detail: DepositDetail) : TransactionDetail()
    data class Unknown(val data: HistoryDetailData, val rawDetail: JsonElement) : TransactionDetail()
}

fun parseTransactionDetail(data: HistoryDetailData, detailJson: JsonElement): TransactionDetail {
    val gson = Gson()
    val type = data.type
    return when(type.lowercase()) {
        "token listrik prabayar" -> {
            val tokenDetail = gson.fromJson(detailJson, TokenListrikDetail::class.java)
            TransactionDetail.Token(data, tokenDetail)
        }
        "top-up e-wallet" -> {
            val eWalletDetail = gson.fromJson(detailJson, EWalletDetail::class.java)
            TransactionDetail.EWallet(data, eWalletDetail)
        }
        "top-up pulsa", "top-up data" -> {
            val pulsaDataDetail = gson.fromJson(detailJson, PulsaDataDetail::class.java)
            TransactionDetail.PulsaData(data, pulsaDataDetail)
        }
        "deposit" -> {
            val depositDetail = gson.fromJson(detailJson, DepositDetail::class.java)
            TransactionDetail.Deposit(data, depositDetail)
        }
        else -> {
            TransactionDetail.Unknown(data, detailJson)
        }
    }
}
