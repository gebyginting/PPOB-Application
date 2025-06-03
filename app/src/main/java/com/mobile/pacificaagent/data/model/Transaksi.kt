package com.mobile.pacificaagent.data.model

data class Transaksi(
    val id: String,
    val jenisTransaksi: String,
    val statusTransaksi: String,
    val imgTransaksi: Int,
    val namaTransaksi: String,
    val waktuTransaksi: String,
    val nominalTransaksi: String
)
