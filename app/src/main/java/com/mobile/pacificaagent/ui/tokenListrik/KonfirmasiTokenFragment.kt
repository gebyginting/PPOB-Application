package com.mobile.pacificaagent.ui.tokenListrik

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
import com.mobile.pacificaagent.data.request.prabayar.TopUpTokenRequest
import com.mobile.pacificaagent.databinding.FragmentKonfirmasiTokenBinding
import com.mobile.pacificaagent.ui.ViewModelFactory
import com.mobile.pacificaagent.ui.auth.UserViewModel
import com.mobile.pacificaagent.ui.viewmodel.TopUpPrabayarViewModel
import com.mobile.pacificaagent.utils.Helper
import com.mobile.pacificaagent.utils.Helper.formatRupiah
import com.mobile.pacificaagent.utils.ResultState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class KonfirmasiTokenFragment : Fragment() {

    private var _binding: FragmentKonfirmasiTokenBinding? = null
    private val binding get() = _binding!!
    private var isSuksesBayar = false

    private val topUpPrabayarViewModel: TopUpPrabayarViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }
    private val userViewModel: UserViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }

    private var tokenRequest: TopUpTokenRequest? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKonfirmasiTokenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        setupDetailPembayaran()
        setupBeliButton()
        setupBackButton()
    }

    private fun setupDetailPembayaran() {
        val args = KonfirmasiTokenFragmentArgs.fromBundle(requireArguments())
        val nomorMeter = args.number
        val jumlah = args.jumlah

        binding.apply {
            tokenRequest = TopUpTokenRequest(
                amount = jumlah,
                nomorMeter = nomorMeter
            )
            tanggalPembayaran.text = Helper.getTanggal()
            tvNoMeter.text = nomorMeter
            tvNominal.text = formatRupiah(jumlah)
            val total = jumlah + 2500
            tvHarga.text = formatRupiah(total)
            tvTotalTagihan.text = formatRupiah(total)
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
                userViewModel.getBalance()
                findNavController().popBackStack(R.id.navigation_home, false)
            } else {
                tokenRequest?.let {
                    showLoading(true)
                    observeTopUpResult()
                    topUpPrabayarViewModel.topupToken(it)
                }
            }
        }
    }

    private fun observeTopUpResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            topUpPrabayarViewModel.topupTokenState.collectLatest { result ->
                when (result) {
                    is ResultState.Loading -> showLoading(true)
                    is ResultState.Success -> {
                        showLoading(false)
                        showResult(result.data.data.detail.hasil.tokenNumber, result.data.data.detail.input.tarifPerKWh)
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

    private fun showResult(token: String, kwh: Int) {
        binding.apply {
            tvTitlePage.visibility = View.GONE
            layoutKodeToken.visibility = View.VISIBLE
            tvTransaksiSukses.visibility = View.VISIBLE
            ivLayananPulsa.visibility = View.VISIBLE
            tanggalPembayaran.text = Helper.getTanggal()
            tanggalPembayaran.visibility = View.VISIBLE
            tvKodeToken.text = token
            tvTarifDaya.text = kwh.toString()
            konfirmasiBtn.text = "Selesai"
            konfirmasiBtn.isEnabled = true
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingOverlay.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.konfirmasiBtn.isEnabled = !isLoading
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}