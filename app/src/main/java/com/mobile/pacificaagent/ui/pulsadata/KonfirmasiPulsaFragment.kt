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
import com.mobile.pacificaagent.data.request.prabayar.Paket
import com.mobile.pacificaagent.data.request.prabayar.Pulsa
import com.mobile.pacificaagent.data.request.prabayar.TopUpDataRequest
import com.mobile.pacificaagent.data.request.prabayar.TopUpPulsaRequest
import com.mobile.pacificaagent.databinding.FragmentKonfirmasiPulsaBinding
import com.mobile.pacificaagent.ui.ViewModelFactory
import com.mobile.pacificaagent.ui.auth.UserViewModel
import com.mobile.pacificaagent.ui.viewmodel.ProdukPrabayarViewModel
import com.mobile.pacificaagent.ui.viewmodel.TopUpPrabayarViewModel
import com.mobile.pacificaagent.utils.Helper
import com.mobile.pacificaagent.utils.Helper.formatRupiah
import com.mobile.pacificaagent.utils.ResultState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class KonfirmasiPulsaFragment : Fragment() {

    private var _binding: FragmentKonfirmasiPulsaBinding? = null
    private val binding get() = _binding!!

    private val produkPrabayarViewModel: ProdukPrabayarViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }
    private val topUpPrabayarViewModel: TopUpPrabayarViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }
    private val userViewModel: UserViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }

    private var isSuksesBayar = false
    private var pulsaRequest: TopUpPulsaRequest? = null
    private var dataRequest: TopUpDataRequest? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKonfirmasiPulsaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        setupBackButton()
        setupDetailPembayaran()
        setupBeliButton()
    }

    private fun setupDetailPembayaran() {
        val args = KonfirmasiPulsaFragmentArgs.fromBundle(requireArguments())
        val number = args.number
        val productType = args.productType // <- ini

        viewLifecycleOwner.lifecycleScope.launch {
            produkPrabayarViewModel.detailProdukState.collectLatest { result ->
                when (result) {
                    is ResultState.Loading -> {
                        showToast("Memuat detail produk...")
                    }

                    is ResultState.Success -> {
                        val dataItem = result.data.data
                        with(binding) {
                            tvNoHp.text = number
                            tvNamaProduk.text = dataItem.productName
                            tvHargaProduk.text = formatRupiah(dataItem.price)
                            tvTotalTagihan.text = formatRupiah(dataItem.price)
                        }

                        // Beda penanganan tergantung jenis produk
                        when (productType) {
                            "pulsa" -> {
                                pulsaRequest = TopUpPulsaRequest(
                                    number = number,
                                    pulsa = Pulsa(
                                        price = dataItem.price,
                                        id = dataItem.id,
                                        operatorId = dataItem.operatorId,
                                        categoryId = dataItem.categoryId,
                                        productName = dataItem.productName,
                                        desc = dataItem.desc,
                                        status = dataItem.status
                                    )
                                )
                            }

                            "data" -> {
                                dataRequest = TopUpDataRequest(
                                    number = number,
                                    paket = Paket( // Pakai sama dulu, bisa dipisah ke TopUpPaketRequest kalau beda struktur
                                        price = dataItem.price,
                                        id = dataItem.id,
                                        operatorId = dataItem.operatorId,
                                        categoryId = dataItem.categoryId,
                                        productName = dataItem.productName,
                                        desc = dataItem.desc,
                                        status = dataItem.status
                                    )
                                )
                            }
                        }
                    }

                    is ResultState.Error -> {
                        Log.e("KonfirmasiPulsa", "Gagal mendapatkan data produk: ${result.error}")
                        showToast("Gagal memuat data produk")
                    }
                }
            }
        }
    }

    private fun setupBeliButton() {
        binding.konfirmasiBtn.setOnClickListener {
            if (isSuksesBayar) {
                userViewModel.clearBalanceCache()
                userViewModel.getBalance()
                findNavController().popBackStack(R.id.navigation_home, false)
            } else {
                val args = KonfirmasiPulsaFragmentArgs.fromBundle(requireArguments())
                when (args.productType) {
                    "pulsa" -> {
                        pulsaRequest?.let { req ->
                            observeTopUpPulsa() // Observe pulsa
                            topUpPrabayarViewModel.topupPulsa(req)
                        } ?: showToast("Data pulsa belum siap")
                    }
                    "data" -> {
                        dataRequest?.let { req ->
                            observeTopUpData() // Observe data
                            topUpPrabayarViewModel.topupData(req)
                        } ?: showToast("Data paket belum siap")
                    }
                    else -> showToast("Tipe produk tidak dikenali")
                }
            }
        }
    }

    private fun observeTopUpPulsa() {
        viewLifecycleOwner.lifecycleScope.launch {
            topUpPrabayarViewModel.topupPulsaState.collectLatest { result ->
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        showLoading(false)
                        showSuccessUI()
                        isSuksesBayar = true
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        showToast(result.error)
                    }
                }
            }
        }
    }

    private fun observeTopUpData() {
        viewLifecycleOwner.lifecycleScope.launch {
            topUpPrabayarViewModel.topupDataState.collectLatest { result ->
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }
                    is ResultState.Success -> {
                        showLoading(false)
                        showSuccessUI()
                        isSuksesBayar = true
                    }
                    is ResultState.Error -> {
                        showLoading(false)
                        showToast("Gagal: ${result.error}")
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

    private fun showSuccessUI() {
        with(binding) {
            tvTitlePage.visibility = View.GONE
            tvTransaksiSukses.visibility = View.VISIBLE
            ivLayananPulsa.setImageResource(R.drawable.ic_berhasil)
            tanggalPembayaran.apply {
                text = Helper.getTanggal()
                visibility = View.VISIBLE
            }
            konfirmasiBtn.apply {
                text = "Selesai"
                isEnabled = true
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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
