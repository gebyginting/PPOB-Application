package com.mobile.pacificaagent.ui.pulsadata

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.databinding.FragmentKonfirmasiPulsaBinding
import com.mobile.pacificaagent.utils.Helper

class KonfirmasiPulsaFragment : Fragment() {

    private var _binding: FragmentKonfirmasiPulsaBinding? = null
    private val binding get() = _binding!!
    private var isSuksesBayar = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentKonfirmasiPulsaBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDetailPembayaran()
        setupBeliButton()
        setupBackButton()
    }

    private fun setupDetailPembayaran() {
        arguments?.let {
            val namaProduk = KonfirmasiPulsaFragmentArgs.fromBundle(it).nama
            val hargaProduk = KonfirmasiPulsaFragmentArgs.fromBundle(it).harga
            val isPulsa = namaProduk.matches(Regex("\\d{1,3}(\\.\\d{3})*")) // contoh: 5.000, 10.000

            with(binding) {
                tvNamaProduk.text = if (isPulsa) "Pulsa Rp$namaProduk" else namaProduk
                tvHargaProduk.text = hargaProduk
                tvTotalTagihan.text = Helper.convertRupiah(hargaProduk)
            }
        }

    }
    private fun setupBackButton() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupBeliButton() {
        with(binding) {
            konfirmasiBtn.setOnClickListener {
                if (isSuksesBayar) {
                    // Kembali ke Home / root fragment
                    findNavController().popBackStack(R.id.navigation_home, false)
                } else {
                    progressBar.visibility = View.VISIBLE
                    konfirmasiBtn.isEnabled = false // agar tidak bisa klik ganda

                    Handler(Looper.getMainLooper()).postDelayed({
                        progressBar.visibility = View.GONE
                        tvTitlePage.visibility = View.GONE
                        tvTransaksiSukses.visibility = View.VISIBLE
                        ivLayananPulsa.setImageResource(R.drawable.ic_berhasil)
                        tanggalPembayaran.apply {
                            text = Helper.getTanggal()
                            visibility = View.VISIBLE
                        }
                        konfirmasiBtn.text = "Selesai"
                        konfirmasiBtn.isEnabled = true
                        isSuksesBayar = true
                    }, 3000)
                }
            }
        }
    }
}