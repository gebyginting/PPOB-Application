package com.mobile.pacificaagent.ui.pulsadata

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.databinding.FragmentKonfirmasiPulsaBinding
import com.mobile.pacificaagent.ui.ViewModelFactory
import com.mobile.pacificaagent.ui.viewmodel.ProdukPrabayarViewModel
import com.mobile.pacificaagent.utils.Helper
import com.mobile.pacificaagent.utils.Helper.formatRupiah
import com.mobile.pacificaagent.utils.ResultState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class KonfirmasiPulsaFragment : Fragment() {

    private var _binding: FragmentKonfirmasiPulsaBinding? = null
    private val binding get() = _binding!!
    private val produkPrabayarViewModel: ProdukPrabayarViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }
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
            viewLifecycleOwner.lifecycleScope.launch {
                produkPrabayarViewModel.detailProdukState.collectLatest { result ->
                    when (result) {
                        is ResultState.Loading -> {
                            Toast.makeText(requireContext(), "LOADING", Toast.LENGTH_SHORT).show()
                        }
                        is ResultState.Success -> {
                            val dataItem = result.data.data
                            dataItem.let {
                                with(binding) {
                                    tvNamaProduk.text = it.productName
                                    tvHargaProduk.text = formatRupiah(it.price)
                                    tvTotalTagihan.text = formatRupiah(it.price)
                                }
                            }
                        }
                        is ResultState.Error -> {
                            Log.d("DATANYA:", result.error)
                        }
                    }
                }
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
                    konfirmasiBtn.isEnabled = false

                    lifecycleScope.launch {
                        delay(3000)
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
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}