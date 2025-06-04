package com.mobile.pacificaagent.utils

import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.data.model.Transaksi
import com.mobile.pacificaagent.data.response.DataHistory
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

    fun convertRupiahToInt(rupiah: String): Int {
        return rupiah
            .replace("Rp", "", ignoreCase = true)
            .replace(".", "")
            .trim()
            .toInt()
    }


    fun formatRupiah(number: Int): String {
        val formatter = NumberFormat.getNumberInstance(Locale("in", "ID"))
        return "Rp${formatter.format(number)}"
    }

    fun parseErrorMessage(jsonString: String?): String {
        return try {
            val json = org.json.JSONObject(jsonString ?: "")
            json.getString("errors") // sesuaikan dengan struktur JSON dari server kamu
        } catch (e: Exception) {
            "Terjadi kesalahan"
        }
    }

    fun DataHistory.toTransaksi(): Transaksi {
        return Transaksi(
            id = id,
            jenisTransaksi = type,
            statusTransaksi = status,
            imgTransaksi = when (type) {
                "TOP-UP E-WALLET" -> R.drawable.ic_ewallet
                "TOP-UP TOKEN" -> R.drawable.ic_pln
                "TOP-UP PULSA" -> R.drawable.ic_pulsa
                else -> R.drawable.ic_ewallet_transaksi // default icon
            },
            namaTransaksi = "Transaksi: $type", // bisa kamu sesuaikan kalau ada nama di model lain
            waktuTransaksi = "25 Juni 2025 08.00", // sementara karena `DataHistory` tidak punya field waktu
            nominalTransaksi = formatRupiah(amount) // bisa pakai formatter kalau mau rapi
        )
    }
}