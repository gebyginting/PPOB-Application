package com.mobile.pacificaagent.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Helper {
    fun convertRupiah(rupiah: String): String {
        return rupiah
            .replace("Rp", "")
            .replace(".", "")// Hilangkan simbol "Rp"
            .trim()                 // Hilangkan spasi di awal/akhir
    }
    fun getTanggal(): String {
        val locale = Locale("id", "ID") // Untuk Bahasa Indonesia
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", locale)
        val tanggalSekarang = Date()
        return dateFormat.format(tanggalSekarang)
    }

    fun formatToRupiah(amount: Int): String {
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(amount).replace(",00", "")
    }
}