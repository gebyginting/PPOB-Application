package com.mobile.pacificaagent.ui.tagihanInternet

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.databinding.FragmentKonfirmasiWifiBinding
import com.mobile.pacificaagent.utils.Helper

class KonfirmasiWifiFragment : Fragment() {
    private var _binding: FragmentKonfirmasiWifiBinding? = null
    private val binding get() = _binding!!
    private var isSuksesBayar = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentKonfirmasiWifiBinding.inflate(inflater, container, false)
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
            val namaProvider = KonfirmasiWifiFragmentArgs.fromBundle(it).namaProvider
            val hargaTagihan = KonfirmasiWifiFragmentArgs.fromBundle(it).hargaTagihan.toInt()

            with(binding) {
                tvTanggal.text = Helper.getTanggal()
                tvPenyediaLayanan.text = namaProvider
                tvNominal.text = hargaTagihan.toString()
                val biayaAdmin = Helper.convertRupiah(tvBiayaAdmin.text.toString()).toInt()
                val totalTagihan = hargaTagihan + biayaAdmin
                tvTotalTagihan.text = "Rp${totalTagihan}"
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
                    findNavController().popBackStack(R.id.navigation_home, false)
                } else {
                    progressBar.visibility = View.VISIBLE
                    konfirmasiBtn.isEnabled = false // agar tidak bisa klik ganda

                    Handler(Looper.getMainLooper()).postDelayed({
                        progressBar.visibility = View.GONE
                        tvTitlePage.visibility = View.GONE
                        tvTransaksiSukses.visibility = View.VISIBLE
                        ivLayananPulsa.visibility = View.VISIBLE
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