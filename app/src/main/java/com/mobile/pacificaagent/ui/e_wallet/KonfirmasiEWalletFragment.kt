package com.mobile.pacificaagent.ui.e_wallet

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.databinding.FragmentKonfirmasiEWalletBinding
import com.mobile.pacificaagent.utils.Helper

class KonfirmasiEWalletFragment : Fragment() {
    private var _binding: FragmentKonfirmasiEWalletBinding? = null
    private val binding get() = _binding!!
    private var isSuksesBayar = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentKonfirmasiEWalletBinding.inflate(inflater, container, false)
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
            val noHp = KonfirmasiEWalletFragmentArgs.fromBundle(it).nomor
            val namaProduk = KonfirmasiEWalletFragmentArgs.fromBundle(it).namaProduk
            val hargaProduk = KonfirmasiEWalletFragmentArgs.fromBundle(it).hargaProduk

            with(binding) {
                tanggalPembayaran.text = Helper.getTanggal()
                tvNoHp.text = noHp
                tvNominal.text = namaProduk
                tvHarga.text = hargaProduk
                tvTotalTagihan.text = hargaProduk
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