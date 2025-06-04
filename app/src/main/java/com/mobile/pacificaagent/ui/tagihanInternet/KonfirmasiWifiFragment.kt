package com.mobile.pacificaagent.ui.tagihanInternet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.data.request.pascabayar.TopUpWifiRequest
import com.mobile.pacificaagent.databinding.FragmentKonfirmasiWifiBinding
import com.mobile.pacificaagent.ui.ViewModelFactory
import com.mobile.pacificaagent.ui.auth.UserViewModel
import com.mobile.pacificaagent.ui.viewmodel.ProdukPascabayarViewModel
import com.mobile.pacificaagent.utils.Helper
import com.mobile.pacificaagent.utils.Helper.formatRupiah
import com.mobile.pacificaagent.utils.ResultState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class KonfirmasiWifiFragment : Fragment() {
    private var _binding: FragmentKonfirmasiWifiBinding? = null
    private val binding get() = _binding!!
    private var isSuksesBayar = false
    private lateinit var topupRequest: TopUpWifiRequest
    private val topUpPascabayarViewModel: ProdukPascabayarViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }
    private val userViewModel: UserViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }

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

        observeTopUpResult()
        setupUI()
    }

    private fun setupUI() {
        setupDetailPembayaran()
        setupBeliButton()
        setupBackButton()
    }

    private fun setupDetailPembayaran() {
        binding.apply {
            val args = KonfirmasiWifiFragmentArgs.fromBundle(requireArguments())
            topupRequest = args.topUpRequest

            tvTanggal.text = Helper.getTanggal()
            tvPenyediaLayanan.text = topupRequest.wifiBill.operator
            tvNominal.text = formatRupiah(topupRequest.wifiBill.tagihan)
            tvTotalTagihan.text = formatRupiah(topupRequest.wifiBill.tagihan)
        }


    }

    private fun setupBackButton() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupBeliButton() {
        binding.konfirmasiBtn.setOnClickListener {
            if (isSuksesBayar) {
                userViewModel.clearBalanceCache()
                userViewModel.getBalance()
                findNavController().popBackStack(R.id.navigation_home, false)
            } else {
                topupRequest.let {
                    showLoading(true)
            //                    observeTopUpResult()
                    topUpPascabayarViewModel.topupWifi(it)
                }
            }
        }
    }

    private fun observeTopUpResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            topUpPascabayarViewModel.topupWifiState.collectLatest { result ->
                when (result) {
                    is ResultState.Loading -> showLoading(true)
                    is ResultState.Success -> {
                        showLoading(false)
                        showResult()
                        isSuksesBayar = true
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        showToast(result.error)
                        binding.konfirmasiBtn.isEnabled = true
                    }
                }
            }
        }
    }

    private fun showResult() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            konfirmasiBtn.isEnabled = false

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
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingOverlay.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.konfirmasiBtn.isEnabled = !isLoading
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}