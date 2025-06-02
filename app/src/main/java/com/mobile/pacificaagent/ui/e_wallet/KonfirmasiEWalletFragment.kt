package com.mobile.pacificaagent.ui.e_wallet

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
import com.mobile.pacificaagent.data.request.prabayar.Ewallet
import com.mobile.pacificaagent.data.request.prabayar.TopUpEWalletRequest
import com.mobile.pacificaagent.databinding.FragmentKonfirmasiEWalletBinding
import com.mobile.pacificaagent.ui.ViewModelFactory
import com.mobile.pacificaagent.ui.auth.UserViewModel
import com.mobile.pacificaagent.ui.viewmodel.TopUpPrabayarViewModel
import com.mobile.pacificaagent.utils.Helper
import com.mobile.pacificaagent.utils.Helper.convertRupiahToInt
import com.mobile.pacificaagent.utils.ResultState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class KonfirmasiEWalletFragment : Fragment() {
    private var _binding: FragmentKonfirmasiEWalletBinding? = null
    private val binding get() = _binding!!
    private var isSuksesBayar = false
    private var topupRequest: TopUpEWalletRequest? = null

    private val topUpPrabayarViewModel: TopUpPrabayarViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }
    private val userViewModel: UserViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }

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
        observeTopUpResult()
        setupUI()
    }

    private fun setupUI() {
        setupDetailPembayaran()
        setupBeliButton()
        setupBackButton()
    }

    private fun setupDetailPembayaran() {
        val args = KonfirmasiEWalletFragmentArgs.fromBundle(requireArguments())
        val noHp = args.nomor
        val namaEWallet = args.namaEWallet
        val namaProduk = args.namaProduk
        val hargaProduk = args.hargaProduk

        binding.apply {
            topupRequest = TopUpEWalletRequest(
                number = noHp,
                ewallet = Ewallet(
                    nameApps = namaEWallet,
                    nominal = namaProduk.replace(".", "").toInt(),
                    harga = convertRupiahToInt(hargaProduk)
                )
            )
            tanggalPembayaran.text = Helper.getTanggal()
            tvNoHp.text = noHp
            tvNominal.text = namaProduk
            tvHarga.text = hargaProduk
            tvMetode.text = namaEWallet
            tvTotalTagihan.text = hargaProduk
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
                topupRequest?.let {
                    showLoading(true)
//                    observeTopUpResult()
                    topUpPrabayarViewModel.topupEWallet(it)
                }
            }
        }
    }

    private fun observeTopUpResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            topUpPrabayarViewModel.topupEWalletState.collectLatest { result ->
                when (result) {
                    is ResultState.Loading -> showLoading(true)
                    is ResultState.Success -> {
                        showLoading(false)
                        showResult(result.data.data.detail.nameApps)
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

    private fun showResult(metode: String) {
        binding.apply {
            tvTitlePage.visibility = View.GONE
            layoutKodeToken.visibility = View.VISIBLE
            tvTransaksiSukses.visibility = View.VISIBLE
            ivLayananPulsa.visibility = View.VISIBLE
            tanggalPembayaran.text = Helper.getTanggal()
            tanggalPembayaran.visibility = View.VISIBLE
            tvMetode.text = metode
            konfirmasiBtn.text = "Selesai"
            konfirmasiBtn.isEnabled = true
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.konfirmasiBtn.isEnabled = !isLoading
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}